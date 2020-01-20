package com.alexkn.syntact.presentation.deckdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
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

        viewModel.deckDetail.observe(viewLifecycleOwner, Observer {
            header.text = it.name
            phrasesOnDeviceTextView.text = String.format("%d/%d available offline", it.onDeviceCount, it.itemCount)
            if (it.onDeviceCount == it.itemCount) {
                downloadButton.setImageResource(R.drawable.ic_offline_pin_black_24dp)
                downloadButton.isEnabled = false
            } else {
                downloadButton.setImageResource(R.drawable.ic_get_app_black_24dp)
                downloadButton.isEnabled = true
            }
        })

        downloadButton.setOnClickListener { viewModel.download() }
    }

    private fun setupItemList() {
        val solvableItemsListAdapter = SolvableItemsListAdapter()
        solvableItemsListAdapter.deleteItemListener = Consumer { viewModel.disableItem(it) }
        itemList.adapter = solvableItemsListAdapter
        val layoutManager = LinearLayoutManager(this.context)
        itemList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        itemList.addItemDecoration(dividerItemDecoration)

        viewModel.translations.observe(viewLifecycleOwner, Observer(solvableItemsListAdapter::submitList))
    }
}