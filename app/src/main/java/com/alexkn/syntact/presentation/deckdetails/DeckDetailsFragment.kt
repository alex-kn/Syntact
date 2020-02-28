package com.alexkn.syntact.presentation.deckdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.deck_details_fragment.*
import java.util.function.Consumer


class DeckDetailsFragment : Fragment() {

    private lateinit var viewModel: DeckDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.deck_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders
                .of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.bucketDetailsViewModelFactory())
                .get(DeckDetailsViewModel::class.java)

        viewModel.init(DeckDetailsFragmentArgs.fromBundle(arguments!!).deckId)

        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }

        setupItemList()


        val sheet = BottomSheetBehavior.from(contentLayout)
        with(sheet) {
            isFitToContents = false
            isHideable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        deckDetailsBackdropButton.setOnClickListener {
            when (sheet.state) {
                BottomSheetBehavior.STATE_EXPANDED -> collapse(sheet)
                else -> expand(sheet)
            }
        }

        deckDetailsTopLayout.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) collapse(sheet) }
        deckDetailsContentHeader.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_COLLAPSED) expand(sheet) }



        viewModel.deckDetail.observe(viewLifecycleOwner, Observer {
            //            header.text = it.name
        })

    }

    private fun setupItemList() {
        val solvableItemsListAdapter = SolvableItemsListAdapter()
        solvableItemsListAdapter.deleteItemListener = Consumer { TODO("DELETE ITEM") }
        itemList.adapter = solvableItemsListAdapter
        val layoutManager = LinearLayoutManager(this.context)
        itemList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        itemList.addItemDecoration(dividerItemDecoration)

        viewModel.translations.observe(viewLifecycleOwner, Observer(solvableItemsListAdapter::submitList))
    }

    private fun expand(sheetBehavior: BottomSheetBehavior<CoordinatorLayout>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        deckDetailsBackdropButton.rotation = 180f
        deckDetailsBackdropButton.animate().rotation(270f).alpha(0.5f).setDuration(100).withEndAction {
            deckDetailsBackdropButton.setImageResource(R.drawable.ic_baseline_show_chart_24)
            deckDetailsBackdropButton.animate().rotation(360f).alpha(1f).setDuration(100).start()
        }.start()
    }

    private fun collapse(sheetBehavior: BottomSheetBehavior<CoordinatorLayout>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        deckDetailsBackdropButton.rotation = 0f
        deckDetailsBackdropButton.animate().rotation(90f).alpha(0.5f).setDuration(100).withEndAction {
            deckDetailsBackdropButton.setImageResource(R.drawable.ic_baseline_clear_24)
            deckDetailsBackdropButton.animate().rotation(180f).alpha(1f).setDuration(100).start()
        }.start()
    }
}