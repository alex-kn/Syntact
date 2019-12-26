package com.alexkn.syntact.presentation.bucketlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.bucket_details.*
import kotlinx.android.synthetic.main.bucket_list_fragment.*


class PlayMenuFragment : Fragment() {

    private lateinit var fab: FloatingActionButton

    private lateinit var viewModel: PlayMenuViewModel

    private var goal: Int = 20

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.bucket_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.playMenuViewModelFactory())
                .get(PlayMenuViewModel::class.java)

        createBucketFab.setOnClickListener(this::newBucket)

        val linearLayoutManager = LinearLayoutManager(this.context)
        languagesList.layoutManager = linearLayoutManager
        val bucketAdapter = BucketAdapter()
        languagesList.adapter = bucketAdapter

        viewModel.playerStats.observe(viewLifecycleOwner, Observer {
            todayView.text = it.solvedToday.toString() + "/" + goal
            progressBar.progress = it.solvedToday
        })
        progressBar.max = goal

        viewModel.buckets.observe(viewLifecycleOwner, Observer(bucketAdapter::submitList))
    }


    private fun newBucket(view: View) {

        val action = PlayMenuFragmentDirections.actionPlayMenuFragmentToCreateBucketFragment()
        Navigation.findNavController(view).navigate(action)
    }
}
