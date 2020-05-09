package dev.alexknittel.syntact.presentation.deckdetails

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dev.alexknittel.syntact.R
import dev.alexknittel.syntact.app.general.config.ApplicationComponentProvider
import dev.alexknittel.syntact.presentation.common.animateIn
import dev.alexknittel.syntact.presentation.common.animateOut
import dev.alexknittel.syntact.presentation.common.flagDrawableOf
import kotlinx.android.synthetic.main.deck_details_fragment.*


class DeckDetailsFragment : Fragment() {

    private lateinit var viewModel: DeckDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.deck_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.bucketDetailsViewModelFactory())
                .get(DeckDetailsViewModel::class.java)

        viewModel.init(DeckDetailsFragmentArgs.fromBundle(arguments!!).deckId)

        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }


        setupItemList()

        val sheet = BottomSheetBehavior.from(contentLayout)
        with(sheet) {
            isFitToContents = false
            isHideable = false
            state = BottomSheetBehavior.STATE_EXPANDED
            expand(this)
        }

        deckDetailsBackdropButton.setOnClickListener {
            when (sheet.state) {
                BottomSheetBehavior.STATE_EXPANDED -> collapse(sheet)
                else -> expand(sheet)
            }
        }

        deckDetailsTopLayout.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) collapse(sheet) }
        deckDetailsContentHeader.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_COLLAPSED) expand(sheet) }

        viewModel.deck.observe(viewLifecycleOwner, Observer {
            it?.let {
                headerExpanded.text = it.name
                deckDetailsLeftLangOutput.text = it.language.displayLanguage
                deckDetailsRightLangOutput.text = it.userLanguage.displayLanguage
                deckDetailsLeftLangFlag.setImageDrawable(resources.flagDrawableOf(it.language))
                deckDetailsRightLangFlag.setImageDrawable(resources.flagDrawableOf(it.userLanguage))
                deckDetailsNameInput.setText(it.name)
                deckDetailsCardsPerDayInput.setText("${it.newItemsPerDay}")
            }
        })

        deckDetailsLeftLangFlag.clipToOutline = true
        deckDetailsRightLangFlag.clipToOutline = true

        deckDetailsNameInput.addTextChangedListener { if (!it.isNullOrBlank() && sheet.state == BottomSheetBehavior.STATE_COLLAPSED) deckDetailsSaveFab.show() else deckDetailsSaveFab.hide() }
        deckDetailsCardsPerDayInput.addTextChangedListener { if (!it.isNullOrBlank() && sheet.state == BottomSheetBehavior.STATE_COLLAPSED) deckDetailsSaveFab.show() else deckDetailsSaveFab.hide() }

        deckDetailsSaveFab.setOnClickListener {
            viewModel.save(deckDetailsNameInput.text.toString().trim(), deckDetailsCardsPerDayInput.text.toString().trim())
            expand(sheet)
            deckDetailsSaveFab.hide()
            Handler().postDelayed({ Snackbar.make(requireView(), "Deck saved.", Snackbar.LENGTH_SHORT).show() }, 200)
        }

        deckDetailsDeleteDeckButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete Deck?")
                    .setMessage("Really delete the Deck with all of its cards? This can not be undone!")
                    .setPositiveButton("Delete") { _, _ ->
                        viewModel.deleteDeck()
                        Navigation.findNavController(it).popBackStack()
                        Snackbar.make(activity!!.findViewById(R.id.nav_host_fragment), "Deck deleted.", Snackbar.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
        }

    }

    private fun setupItemList() {
        val solvableItemsListAdapter = SolvableItemsListAdapter()
        solvableItemsListAdapter.onDeleteListener = { viewModel.deleteCard(it) }
        itemList.adapter = solvableItemsListAdapter
        val layoutManager = LinearLayoutManager(this.context)
        itemList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        itemList.addItemDecoration(dividerItemDecoration)

        viewModel.translations.observe(viewLifecycleOwner, Observer { translations ->
            solvableItemsListAdapter.submitList(translations.sortedBy { it.solvableItem.nextDueDate })
            totalCardsOutput.text = translations.size.toString()
        })
    }

    private fun expand(sheetBehavior: BottomSheetBehavior<CoordinatorLayout>) {
        deckDetailsSaveFab.visibility = View.INVISIBLE
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        deckDetailsBackdropButton.rotation = 180f
        deckDetailsBackdropButton.animate().rotation(270f).alpha(0.5f).setDuration(100).withEndAction {
            deckDetailsBackdropButton.setImageResource(R.drawable.ic_baseline_build_24)
            deckDetailsBackdropButton.animate().rotation(360f).alpha(1f).setDuration(100).start()
        }.start()
        animateIn(headerExpanded)
        animateOut(headerCollapsed)
        resetInputs()
    }

    private fun resetInputs() {
        deckDetailsNameInput.setText(viewModel.deck.value?.name)
        deckDetailsCardsPerDayInput.setText("${viewModel.deck.value?.newItemsPerDay}")
    }

    private fun collapse(sheetBehavior: BottomSheetBehavior<CoordinatorLayout>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        deckDetailsBackdropButton.rotation = 0f
        deckDetailsBackdropButton.animate().rotation(90f).alpha(0.5f).setDuration(100).withEndAction {
            deckDetailsBackdropButton.setImageResource(R.drawable.ic_baseline_clear_24)
            deckDetailsBackdropButton.animate().rotation(180f).alpha(1f).setDuration(100).start()
        }.start()
        animateIn(headerCollapsed)
        animateOut(headerExpanded)
    }


}