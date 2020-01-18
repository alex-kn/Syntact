package com.alexkn.syntact.presentation.deckselection

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
import kotlinx.android.synthetic.main.deck_selection_fragment.*
import kotlinx.android.synthetic.main.deck_selection_language_sheet.*
import java.util.*
import java.util.function.Consumer


class DeckSelectionFragment : Fragment() {

    private lateinit var viewModel: DeckSelectionViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private var selectedLanguage: Locale? = null
    private var filteredTemplates: MediatorLiveData<List<Template>> = MediatorLiveData()

    private lateinit var deckSelectionLanguageAdapter: DeckSelectionLanguageAdapter
    private lateinit var deckSelectionItemAdapter: DeckSelectionItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.deck_selection_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.createBucketViewModelFactory())
                .get(DeckSelectionViewModel::class.java)
        bottomSheetBehavior = BottomSheetBehavior.from(languageSheet)
        setupLanguageList()
        setupTemplateList()
        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }
    }

    private fun setupLanguageList() {
        val languageLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        languageRecyclerView.layoutManager = languageLayoutManager
        deckSelectionLanguageAdapter = DeckSelectionLanguageAdapter(viewModel.availableLanguages)
        languageRecyclerView.adapter = deckSelectionLanguageAdapter

        deckSelectionLanguageAdapter.getLanguage().observe(viewLifecycleOwner, onLanguageChanged)
        bottomSheetBehavior.addBottomSheetCallback(LanguageBottomSheetBehavior())
        bottomSheetBehavior.isHideable = false
        flagImage.clipToOutline = true
    }

    private fun setupTemplateList() {
        val templateLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        selectTemplateRecyclerView.layoutManager = templateLayoutManager
        deckSelectionItemAdapter = DeckSelectionItemAdapter()
        selectTemplateRecyclerView.adapter = deckSelectionItemAdapter
        LinearSnapHelper().attachToRecyclerView(selectTemplateRecyclerView)
        selectTemplateRecyclerView.visibility = View.GONE

        filteredTemplates.addSource(viewModel.availableTemplates) {
            filteredTemplates.value = it.filter { t -> selectedLanguage?.let { t.language == selectedLanguage } ?: true }
        }

        filteredTemplates.addSource(deckSelectionLanguageAdapter.getLanguage()) {
            val list = viewModel.availableTemplates.value
            filteredTemplates.value = list?.filter { t -> t.language == it }
        }
        filteredTemplates.observe(viewLifecycleOwner, onTemplatesChanged)

        deckSelectionItemAdapter.createListener = Consumer {
            viewModel.addBucketFromExistingTemplate(it)
            Navigation.findNavController(view!!).popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        filteredTemplates.removeSource(viewModel.availableTemplates)
        filteredTemplates.removeSource(deckSelectionLanguageAdapter.getLanguage())
    }

    private val onTemplatesChanged = Observer { templates: List<Template> ->
        deckSelectionItemAdapter.submitList(templates)
        progressBar2.visibility = View.GONE
        selectTemplateRecyclerView.visibility = View.VISIBLE
        when (templates.size) {
            1 -> chooseLanguageSheetLabel.text = "1 Template"
            else -> chooseLanguageSheetLabel.text = "%d Templates".format(templates.size)
        }
    }

    private val onLanguageChanged = Observer { locale: Locale ->
        selectedLanguage = locale
        val resId = context!!.resources.getIdentifier(locale.language, "drawable", context!!.packageName)
        flagImage.setImageResource(resId)
        Handler().postDelayed({ bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED }, 300)
    }

    inner class LanguageBottomSheetBehavior : BottomSheetBehavior.BottomSheetCallback() {

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            arrowUp.rotation = slideOffset * -180
            flagImage.alpha = (1 - slideOffset)
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) = Unit
    }


}


