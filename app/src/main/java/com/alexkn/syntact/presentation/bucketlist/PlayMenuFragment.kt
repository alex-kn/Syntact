package com.alexkn.syntact.presentation.bucketlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.app.TAG
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PlayMenuFragment : Fragment() {

    private lateinit var fab: FloatingActionButton

    private lateinit var viewModel: PlayMenuViewModel

    private var languagesList: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.play_menu_fragment, container, false)

        viewModel = ViewModelProviders.of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.playMenuViewModelFactory())
                .get(PlayMenuViewModel::class.java)

        fab = view.findViewById<FloatingActionButton>(R.id.createBucketFab)
        fab.setOnClickListener(this::newBucket)


        languagesList = view.findViewById(R.id.languagesList)
        languagesList!!.layoutManager = LinearLayoutManager(this.context)
        val bucketAdapter = BucketAdapter()
        languagesList!!.adapter = bucketAdapter
        languagesList!!.setHasFixedSize(true)

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteBucket(bucketAdapter.list[viewHolder.adapterPosition].id)
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(languagesList)


        viewModel.buckets.observe(viewLifecycleOwner, Observer(bucketAdapter::submitList))
        return view
    }

    private fun newBucket(view: View) {

        val action = PlayMenuFragmentDirections.actionPlayMenuFragmentToCreateBucketFragment()
        Navigation.findNavController(view).navigate(action)
    }
}
