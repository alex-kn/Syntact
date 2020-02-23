package com.alexkn.syntact.presentation.decklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.deck_list_fragment.*


class DeckListFragment : Fragment() {

    private lateinit var viewModel: DeckListViewModel

    private var goal: Int = 20

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.deck_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.playMenuViewModelFactory())
                .get(DeckListViewModel::class.java)


        val sheet = BottomSheetBehavior.from(contentLayout)
        with(sheet) {
            isFitToContents = false
            isHideable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        backdropButton.setOnClickListener {
            when (sheet.state) {
                BottomSheetBehavior.STATE_EXPANDED -> collapse(sheet)
                else -> expand(sheet)
            }
        }

        topLayout.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) collapse(sheet) }
        contentHeader.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_COLLAPSED) expand(sheet) }

        createBucketFab.setOnClickListener(this::createBucket)

        setupDeckList()

        viewModel.newCards.observe(viewLifecycleOwner, Observer {
            newCardsSettingsOutput.text = it.toString()
            newOutput.text = "$it"
        })

        viewModel.reviews.observe(viewLifecycleOwner, Observer {
            reviewsSettingsOutput.text = it.toString()
            reviewsSettingsOutput.animate().alpha(1f).rotation(0f).setDuration(100).start()
            reviewsSettingsLabel.animate().alpha(1f).setDuration(100).start()
            reviewsOutput.text = "$it"
        })

        viewModel.init()
    }

    private fun setupDeckList() {
        val linearLayoutManager = LinearLayoutManager(this.context)
        languagesList.layoutManager = linearLayoutManager
        val bucketAdapter = DeckListItemAdapter()
        languagesList.adapter = bucketAdapter
        viewModel.buckets.observe(viewLifecycleOwner, Observer(bucketAdapter::submitList))
    }

    private fun newBucket(view: View) {
        val action = DeckListFragmentDirections.actionDeckListFragmentToDeckSelectionFragment()
        Navigation.findNavController(view).navigate(action)
    }

    private fun createBucket(view: View) {
        val action = DeckListFragmentDirections.actionDeckListFragmentToDeckCreationFragment()
        Navigation.findNavController(view).navigate(action)
    }

    private fun expand(sheetBehavior: BottomSheetBehavior<LinearLayout>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        backdropButton.rotation = 180f
        backdropButton.animate().rotation(270f).alpha(0.5f).setDuration(100).withEndAction {
            backdropButton.setImageResource(R.drawable.ic_baseline_account_circle_24)
            backdropButton.animate().rotation(360f).alpha(1f).setDuration(100).start()
        }.start()
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
