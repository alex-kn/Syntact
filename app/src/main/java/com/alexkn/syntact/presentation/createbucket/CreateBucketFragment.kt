package com.alexkn.syntact.presentation.createbucket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.restservice.Template
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.disposables.CompositeDisposable
import java.util.Locale

class CreateBucketFragment : Fragment() {

    private lateinit var viewModel: CreateBucketViewModel

    private var selectedLanguage: Locale? = null

    private var selectedTemplate: Template? = null

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.bucket_create_fragment, container, false)
        viewModel = ViewModelProviders
                .of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.createBucketViewModelFactory())
                .get(CreateBucketViewModel::class.java)

        val backButton = view.findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }


        val dataset = viewModel.availableBuckets
        dataset.remove(Locale.getDefault())

        val languageRecyclerView = view.findViewById<RecyclerView>(R.id.language_recyclerview)
        val languageLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        languageRecyclerView.layoutManager = languageLayoutManager
        val adapter = ChooseLanguageAdapter(dataset)
        languageRecyclerView.adapter = adapter


        val disposable = adapter.getLanguage()?.subscribe { locale -> selectedLanguage = locale }
        compositeDisposable.add(disposable)


        val templateRecyclerView = view.findViewById<RecyclerView>(R.id.selectTemplateRecyclerView)
        val templateLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        templateRecyclerView.layoutManager = templateLayoutManager

        val chooseTemplateAdapter = ChooseTemplateAdapter()
        templateRecyclerView.adapter = chooseTemplateAdapter
        val linearSnapHelper1 = LinearSnapHelper()

        linearSnapHelper1.attachToRecyclerView(templateRecyclerView)

        templateRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val itemPosition = templateLayoutManager.findFirstVisibleItemPosition()
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        selectedTemplate = chooseTemplateAdapter.list[itemPosition]
                        Log.i(TAG, "onScrollStateChanged: " + selectedLanguage?.displayLanguage)
                    }
                }
            }
        })

        /*addButton!!.setOnClickListener {
            viewModel.addBucket(selectedLanguage!!, selectedTemplate, "")//TODO
            Navigation.findNavController(view).popBackStack()
        }*/

        viewModel.availableTemplates.observe(viewLifecycleOwner, Observer(chooseTemplateAdapter::submitList))

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }
}
