package com.alexkn.syntact.presentation.decklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val sheetBehavior = BottomSheetBehavior.from(contentLayout)
        with(sheetBehavior) {
            isFitToContents = false
            isHideable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        backdropButton.setOnClickListener {
            when (sheetBehavior.state) {
                BottomSheetBehavior.STATE_EXPANDED -> collapse(sheetBehavior)
                else -> expand(sheetBehavior)
            }
        }

        topLayout.setOnClickListener {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) collapse(sheetBehavior)
        }

        contentHeader.setOnClickListener {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) expand(sheetBehavior)
        }

        createBucketFab.setOnClickListener(this::newBucket)

        setupDeckList()

        reviewsOutput.alpha = 0f
        reviewsOutput.rotation = -45f
        reviewsLabel.alpha = 0f
        newCardsOutput.alpha = 0f
        newCardsOutput.rotation = -45f
        newCardsLabel.alpha = 0f

        viewModel.playerStats.observe(viewLifecycleOwner, Observer {
            todayView.text = it.solvedToday.toString() + "/" + goal
        })

        viewModel.newCards.observe(viewLifecycleOwner, Observer {
            newCardsOutput.text = it.toString()
            newCardsOutput.animate().alpha(1f).rotation(0f).setDuration(100).start()
            newCardsLabel.animate().alpha(1f).setDuration(100).start()
        })

        viewModel.reviews.observe(viewLifecycleOwner, Observer {
            reviewsOutput.text = it.toString()
            reviewsOutput.animate().alpha(1f).rotation(0f).setDuration(100).start()
            reviewsLabel.animate().alpha(1f).setDuration(100).start()
        })

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

    private fun expand(sheetBehavior: BottomSheetBehavior<LinearLayout>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        backdropButton.rotation = 180f
        backdropButton.animate().rotation(270f).alpha(0.5f).setDuration(100).withEndAction {
            backdropButton.setImageResource(R.drawable.ic_baseline_account_circle_24)
            backdropButton.animate().rotation(360f).alpha(1f).setDuration(100).start()
        }.start()
    }

    private fun collapse(sheetBehavior: BottomSheetBehavior<LinearLayout>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        backdropButton.rotation = 0f
        backdropButton.animate().rotation(90f).alpha(0.5f).setDuration(100).withEndAction {
            backdropButton.setImageResource(R.drawable.ic_baseline_clear_24)
            backdropButton.animate().rotation(180f).alpha(1f).setDuration(100).start()
        }.start()
    }

}
