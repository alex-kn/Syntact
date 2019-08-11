package com.alexkn.syntact.presentation.bucketdetails

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
import kotlinx.android.synthetic.main.bucket_details.*
import java.util.function.Consumer


class BucketDetailsFragment : Fragment() {

    private lateinit var viewModel: BucketDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.bucket_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders
                .of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.bucketDetailsViewModelFactory())
                .get(BucketDetailsViewModel::class.java)

        val bucketId = BucketDetailsFragmentArgs.fromBundle(arguments!!).bucketId
        viewModel.init(bucketId)

        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }

        val solvableItemsListAdapter = SolvableItemsListAdapter()
        solvableItemsListAdapter.deleteItemListener = Consumer { viewModel.disableItem(it) }
        itemList.adapter = solvableItemsListAdapter
        val layoutManager = LinearLayoutManager(this.context)
        itemList.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        itemList.addItemDecoration(dividerItemDecoration)

        viewModel.translations.observe(viewLifecycleOwner, Observer(solvableItemsListAdapter::submitList))
        viewModel.bucketDetail.observe(viewLifecycleOwner, Observer {
            header.text = it.name
            phrasesOnDeviceTextView.text = String.format("%d/%d available offline", it.onDeviceCount, it.itemCount  - it.disabledCount)
            if (it.onDeviceCount == it.itemCount - it.disabledCount) {
                downloadButton.setImageResource(R.drawable.ic_offline_pin_black_24dp)
                downloadButton.isEnabled = false
            } else {
                downloadButton.setImageResource(R.drawable.ic_get_app_black_24dp)
                downloadButton.isEnabled = true
            }
        })

        downloadButton.setOnClickListener {
            viewModel.download()
        }
    }
}