package com.alexkn.syntact.presentation.decklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.presentation.MainActivity
import com.alexkn.syntact.presentation.decklist.dialog.DeckListLanguageDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.deck_list_fragment.*
import java.util.*


class DeckListFragment : Fragment() {

    private lateinit var sheet: BottomSheetBehavior<LinearLayout>
    private lateinit var viewModel: DeckListViewModel
    private lateinit var dialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.deck_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.playMenuViewModelFactory())
                .get(DeckListViewModel::class.java)


        sheet = BottomSheetBehavior.from(contentLayout)
        sheet.isFitToContents = false
        sheet.isHideable = false
        if (savedInstanceState?.let { it.getBoolean("collapsed") } == true) collapse(sheet) else expand(sheet)

        backdropButton.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) collapse(sheet) else expand(sheet) }
        topLayout.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) collapse(sheet) }
        contentHeader.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_COLLAPSED) expand(sheet) }

        createBucketFab.setOnClickListener { createBucket(it) }

        deckListNightModeOutput.setOnClickListener { viewModel.switchNightMode() }

        setupDeckList()

        viewModel.newCards.observe(viewLifecycleOwner, Observer { newOutput.text = "$it" })
        viewModel.reviews.observe(viewLifecycleOwner, Observer { reviewsOutput.text = "$it" })
        viewModel.total.observe(viewLifecycleOwner, Observer {
            deckListContentHeaderLabel.text = when (it) {
                0 -> resources.getString(R.string.deck_list_list_header_done)
                else -> resources.getString(R.string.deck_list_list_header_not_done)
            }
        })

        viewModel.preferences.observe(viewLifecycleOwner, Observer {
            it?.let {
                userLanguageOutput.text = it.language.displayLanguage
                (requireActivity() as MainActivity).setNightMode(it.nightMode)
                deckListNightModeOutput.text = when (it.nightMode) {
                    AppCompatDelegate.MODE_NIGHT_NO -> resources.getString(R.string.no)
                    AppCompatDelegate.MODE_NIGHT_YES -> resources.getString(R.string.yes)
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> resources.getString(R.string.auto)
                    else -> ""
                }
                buildLanguageDialog()
                viewModel.refreshDeckList(it.language)
            }
        })
        userLanguageOutput.setOnClickListener { dialog.show() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("collapsed", sheet.state == BottomSheetBehavior.STATE_COLLAPSED)
    }

    private fun setupDeckList() {
        val linearLayoutManager = LinearLayoutManager(this.context)
        languagesList.layoutManager = linearLayoutManager
        val bucketAdapter = DeckListItemAdapter()
        languagesList.adapter = bucketAdapter
        languagesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(languagesList, dx, dy)
                if (dy > 0 && createBucketFab.isShown) {
                    createBucketFab.hide()
                } else if (dy < 0 && !createBucketFab.isShown) {
                    createBucketFab.show()
                }
            }
        })

        viewModel.decks.observe(viewLifecycleOwner, Observer {
            bucketAdapter.submitList(it)
            deckListEmptyLabel.isVisible = it.isEmpty()
        })
    }

    private fun buildLanguageDialog() {

        dialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Choose your Language")
                .setItems(viewModel.languageChoices.map { it.displayLanguage }.toTypedArray()) { _, i -> viewModel.switchLanguage(i) }
                .create()
    }

    private fun createBucket(view: View) {

        val userLang = viewModel.preferences.value!!.language

        if (userLang != Locale.ENGLISH) {
            createBucketFab.hide()
            val action = DeckListFragmentDirections.actionDeckListFragmentToDeckCreationFragment(Locale.ENGLISH.language)
            Navigation.findNavController(view).navigate(action)
        } else {
            with(DeckListLanguageDialog()) {
                bindTo(viewModel.languageChoices.map { userLang to it }.filter { it.first != it.second }) {
                    this@DeckListFragment.createBucketFab.hide()
                    val action = DeckListFragmentDirections.actionDeckListFragmentToDeckCreationFragment(it.language)
                    Navigation.findNavController(view).navigate(action)
                    dismiss()
                }
                show((this@DeckListFragment.requireContext() as AppCompatActivity).supportFragmentManager, DeckListLanguageDialog::class.simpleName)
            }
        }

    }

    private fun expand(sheetBehavior: BottomSheetBehavior<LinearLayout>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        backdropButton.rotation = 180f
        backdropButton.animate().rotation(270f).alpha(0.5f).setDuration(100).withEndAction {
            backdropButton.setImageResource(R.drawable.ic_baseline_account_circle_24)
            backdropButton.animate().rotation(360f).alpha(1f).setDuration(100).start()
        }.start()
        createBucketFab.show()
        animateOut(settingsLabel)
        animateIn(newLabel, newOutput, reviewsLabel, reviewsOutput)
    }

    private fun collapse(sheetBehavior: BottomSheetBehavior<LinearLayout>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        backdropButton.rotation = 0f
        backdropButton.animate().rotation(90f).alpha(0.5f).setDuration(100).withEndAction {
            backdropButton.setImageResource(R.drawable.ic_baseline_clear_24)
            backdropButton.animate().rotation(180f).alpha(1f).setDuration(100).start()
        }.start()
        createBucketFab.hide()
        animateOut(newLabel, newOutput, reviewsLabel, reviewsOutput)
        animateIn(settingsLabel)
    }

    private fun animateOut(vararg views: View) {
        views.forEach {
            it.animate().setDuration(200).alpha(0f).translationXBy(100f).setInterpolator(AccelerateDecelerateInterpolator()).withEndAction {
                it.translationX = 0f
            }.start()
        }
    }

    private fun animateIn(vararg views: View) {
        views.forEach {
            it.translationX = -100f
            it.animate().setDuration(200).alpha(1f).translationXBy(100f).setInterpolator(AccelerateDecelerateInterpolator()).withEndAction {
                it.translationX = 0f
            }.start()
        }
    }
}
