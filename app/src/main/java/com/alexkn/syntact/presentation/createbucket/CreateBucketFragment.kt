package com.alexkn.syntact.presentation.createbucket

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.data.model.Template
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.create_bucket_fragment.*
import kotlinx.android.synthetic.main.create_bucket_language_sheet.*
import java.util.*
import java.util.function.Consumer


class CreateBucketFragment : Fragment() {

    private lateinit var viewModel: CreateBucketViewModel

    private var selectedLanguage: Locale? = null

    private var filteredTemplates: MediatorLiveData<List<Template>> = MediatorLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.create_bucket_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.createBucketViewModelFactory())
                .get(CreateBucketViewModel::class.java)

        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }

        val languageLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        languageRecyclerView.layoutManager = languageLayoutManager
        val chooseLanguageAdapter = ChooseLanguageAdapter(viewModel.availableLanguages)
        languageRecyclerView.adapter = chooseLanguageAdapter

        val bottomSheetBehavior = BottomSheetBehavior.from(languageSheet)

        chooseLanguageAdapter.getLanguage().observe(viewLifecycleOwner, Observer { locale ->
            selectedLanguage = locale
            val resId = context!!.resources.getIdentifier(locale.language, "drawable", context!!.packageName)
            flagImage.setImageResource(resId)
            Handler().postDelayed({ bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED }, 300)
        })

        val templateLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        selectTemplateRecyclerView.layoutManager = templateLayoutManager

        val chooseTemplateAdapter = ChooseTemplateAdapter()
        selectTemplateRecyclerView.adapter = chooseTemplateAdapter
        val linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(selectTemplateRecyclerView)
        selectTemplateRecyclerView.visibility = View.GONE

        chooseLanguageSheetLabel.text = "0 Templates"
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                arrowUp.rotation = slideOffset * -180
                flagImage.alpha = (1 - slideOffset)
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) = Unit
        })
        bottomSheetBehavior.isHideable = false

        flagImage.clipToOutline = true

        chooseTemplateAdapter.createListener = Consumer {
            viewModel.addBucketFromExistingTemplate(it)
            Navigation.findNavController(view).popBackStack()
        }

        filteredTemplates.addSource(viewModel.availableTemplates) {
            filteredTemplates.value = it.filter { t -> selectedLanguage?.let { t.language == selectedLanguage } ?: true }
        }

        filteredTemplates.addSource(chooseLanguageAdapter.getLanguage()) {
            val list = viewModel.availableTemplates.value
            filteredTemplates.value = list?.filter { t -> t.language == it }
        }

        filteredTemplates.observe(viewLifecycleOwner, Observer {
            chooseTemplateAdapter.submitList(it)
            progressBar2.visibility = View.GONE
            selectTemplateRecyclerView.visibility = View.VISIBLE
            when {
                it.size == 1 -> chooseLanguageSheetLabel.text = "1 Template"
                else -> chooseLanguageSheetLabel.text = "%d Templates".format(it.size)
            }
        })
    }

}


