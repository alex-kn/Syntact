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
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.restservice.Template
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.bucket_create_fragment.*
import kotlinx.android.synthetic.main.language_sheet.*
import java.util.*
import java.util.function.Consumer


class CreateBucketFragment : Fragment() {

    private lateinit var viewModel: CreateBucketViewModel

    private var selectedLanguage: Locale? = null

    private var selectedTemplate: Template? = null

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.alexkn.syntact.R.layout.bucket_create_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel = ViewModelProviders
                .of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.createBucketViewModelFactory())
                .get(CreateBucketViewModel::class.java)


        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }


        val dataset = viewModel.availableBuckets
        dataset.remove(Locale.getDefault())

        val languageLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        languageRecyclerView.layoutManager = languageLayoutManager
        val adapter = ChooseLanguageAdapter(dataset)
        languageRecyclerView.adapter = adapter


        createButton.visibility = View.GONE
        val disposable = adapter.getLanguage()?.subscribe { locale ->
            selectedLanguage = locale
            createButton.visibility = View.VISIBLE
        }
        compositeDisposable.add(disposable)


        val templateLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        selectTemplateRecyclerView.layoutManager = templateLayoutManager

        val chooseTemplateAdapter = ChooseTemplateAdapter()
        selectTemplateRecyclerView.adapter = chooseTemplateAdapter
        val linearSnapHelper1 = LinearSnapHelper()

        linearSnapHelper1.attachToRecyclerView(selectTemplateRecyclerView)

        selectTemplateRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val itemPosition = templateLayoutManager.findFirstVisibleItemPosition()
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        selectedTemplate = chooseTemplateAdapter.list[itemPosition]
                    }
                }
            }
        })

        chooseTemplateAdapter.createListener = Consumer {
            viewModel.addBucketFromExistingTemplate(it)
            Navigation.findNavController(view).popBackStack()
        }

        createButton.setOnClickListener {
            viewModel.addBucketFromNewTemplate(selectedLanguage!!, listOf("Brücke", "Bier", "Auto"))
        }


        selectTemplateRecyclerView.visibility = View.GONE
        arrowLeftImageView.visibility = View.GONE
        arrowRightImageView.visibility = View.GONE

        viewModel.availableTemplates.observe(viewLifecycleOwner, Observer {
            chooseTemplateAdapter.submitList(it)
            progressBar2.visibility = View.GONE
            selectTemplateRecyclerView.visibility = View.VISIBLE
            arrowLeftImageView.visibility = View.VISIBLE
            arrowRightImageView.visibility = View.VISIBLE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }
}
