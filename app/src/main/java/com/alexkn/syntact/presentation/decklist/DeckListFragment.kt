package com.alexkn.syntact.presentation.decklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            addBottomSheetCallback(BackdropSheetBehavior())
        }

        arrowUp.setOnClickListener {
            when (sheetBehavior.state) {
                BottomSheetBehavior.STATE_EXPANDED -> sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
                else -> sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            }
        }

        createBucketFab.setOnClickListener(this::newBucket)

        setupDeckList()

        viewModel.playerStats.observe(viewLifecycleOwner, Observer {
            todayView.text = it.solvedToday.toString() + "/" + goal
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


    inner class BackdropSheetBehavior : BottomSheetBehavior.BottomSheetCallback() {

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            arrowUp.rotation = slideOffset * -180
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) = Unit
    }
}
