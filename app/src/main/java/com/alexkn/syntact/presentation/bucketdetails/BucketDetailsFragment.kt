package com.alexkn.syntact.presentation.bucketdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexkn.syntact.app.ApplicationComponentProvider
import kotlinx.android.synthetic.main.bucket_details.*

class BucketDetailsFragment : Fragment() {

    lateinit var viewModel: BucketDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(com.alexkn.syntact.R.layout.bucket_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders
                .of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.bucketDetailsViewModelFactory())
                .get(BucketDetailsViewModel::class.java)

        val bucketId = BucketDetailsFragmentArgs.fromBundle(arguments!!).bucketId
        viewModel.init(bucketId)

        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }

        val solvableItemsListAdapter = SolvableItemsListAdapter()
        itemList.adapter = solvableItemsListAdapter
        itemList.layoutManager = LinearLayoutManager(this.context)

        viewModel.translations.observe(viewLifecycleOwner, Observer(solvableItemsListAdapter::submitList))
        viewModel.bucketDetail.observe(viewLifecycleOwner, Observer { header.text = it.name })
    }
}