package com.alexkn.syntact.presentation.createbucket


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.presentation.common.getSnapPosition
import java.util.*


class CreateBucketLanguagePageFragment : Fragment() {

    private lateinit var viewModel: CreateBucketViewModel
    private var snapPosition = RecyclerView.NO_POSITION

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = layoutInflater.inflate(R.layout.bucket_create_language_page, container, false)
        viewModel = ViewModelProviders
                .of(parentFragment!!, (activity!!.application as ApplicationComponentProvider).applicationComponent.createBucketViewModelFactory())
                .get(CreateBucketViewModel::class.java)

        val dataset = viewModel.availableBuckets

        val languageRecyclerView = view.findViewById<RecyclerView>(R.id.selectLanguageRecyclerView)
        val languageLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        languageRecyclerView.layoutManager = languageLayoutManager
        val adapter = ChooseLanguageAdapter(dataset)
        languageRecyclerView.adapter = adapter
        val linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(languageRecyclerView)

        languageRecyclerView.post {
            val padding = resources.getDimensionPixelSize(R.dimen.keyline_2)
            val rbHeight = languageRecyclerView.height
            val itemWidth = resources.getDimensionPixelSize(android.R.dimen.thumbnail_height)
            val offset = (rbHeight - itemWidth - padding) / 2
            languageLayoutManager.scrollToPositionWithOffset(Int.MAX_VALUE / 2, offset)
        }

        languageRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val tmpSnapPosition = linearSnapHelper.getSnapPosition(recyclerView)
                val snapPositionChanged = snapPosition != tmpSnapPosition
                if (snapPositionChanged) {
                    viewModel.selectedLanguage = adapter.getItemAt(tmpSnapPosition % dataset.size)
                    snapPosition = tmpSnapPosition
                }
            }
        })

        return view
    }


}
