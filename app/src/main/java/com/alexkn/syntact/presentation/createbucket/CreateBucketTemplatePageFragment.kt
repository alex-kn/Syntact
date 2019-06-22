package com.alexkn.syntact.presentation.createbucket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.presentation.common.getSnapPosition
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class CreateBucketTemplatePageFragment : Fragment() {

    private lateinit var viewModel: CreateBucketViewModel
    private var snapPosition = RecyclerView.NO_POSITION

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = layoutInflater.inflate(R.layout.bucket_create_template_page, container, false)
        viewModel = ViewModelProviders
                .of(parentFragment!!, (activity!!.application as ApplicationComponentProvider).applicationComponent.createBucketViewModelFactory())
                .get(CreateBucketViewModel::class.java)

        val addButton = view.findViewById<ExtendedFloatingActionButton>(R.id.addFab)


        val templateRecyclerView = view.findViewById<RecyclerView>(R.id.selectTemplateRecyclerView)
        val languageLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        templateRecyclerView.layoutManager = languageLayoutManager
        val adapter = ChooseTemplateAdapter()
        templateRecyclerView.adapter = adapter
        val linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(templateRecyclerView)

        viewModel.availableTemplates.observe(this, Observer(adapter::submitList))

        templateRecyclerView.post {
            val padding = resources.getDimensionPixelSize(R.dimen.keyline_2)
            val rbHeight = templateRecyclerView.height
            val itemWidth = resources.getDimensionPixelSize(android.R.dimen.thumbnail_height)
            val offset = (rbHeight - itemWidth - padding) / 2
            languageLayoutManager.scrollToPositionWithOffset(Int.MAX_VALUE / 2, offset)
        }

        templateRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val tmpSnapPosition = linearSnapHelper.getSnapPosition(recyclerView)
                val snapPositionChanged = snapPosition != tmpSnapPosition
                if (snapPositionChanged) {
                    viewModel.selectedTemplate = adapter.getItemAt(tmpSnapPosition % adapter.list.size)
                    snapPosition = tmpSnapPosition
                }
            }
        })

        addButton!!.setOnClickListener {
            viewModel.addBucket()
            Navigation.findNavController(view).popBackStack()
        }

        return view
    }
}