package com.alexkn.syntact.presentation.createbucket

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.data.model.Template
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bucket_create_fragment.*
import kotlinx.android.synthetic.main.language_sheet.*
import java.util.*
import java.util.function.Consumer


class CreateBucketFragment : Fragment() {





    private lateinit var viewModel: CreateBucketViewModel

    private var selectedLanguage: Locale? = null

    private var filteredTemplates: MediatorLiveData<List<Template>> = MediatorLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bucket_create_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders
                .of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.createBucketViewModelFactory())
                .get(CreateBucketViewModel::class.java)

        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }


        val dataset = viewModel.availableBuckets.filter { locale -> locale.language != Locale.getDefault().language }

        val languageLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        languageRecyclerView.layoutManager = languageLayoutManager
        val adapter = ChooseLanguageAdapter(dataset)
        languageRecyclerView.adapter = adapter

        val bottomSheetBehavior = BottomSheetBehavior.from(languageSheet)

        adapter.getLanguage().observe(viewLifecycleOwner, Observer { locale ->
            selectedLanguage = locale
            chooseLanguageSheetLabel.setText(selectedLanguage!!.displayLanguage)
            val resId = context!!.resources.getIdentifier(locale.language, "drawable", context!!.packageName)
            flagImage.setImageResource(resId)
            Handler().postDelayed({
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }, 300)
        })


        val templateLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        selectTemplateRecyclerView.layoutManager = templateLayoutManager

        val chooseTemplateAdapter = ChooseTemplateAdapter()
        selectTemplateRecyclerView.adapter = chooseTemplateAdapter
        val linearSnapHelper = LinearSnapHelper()

        linearSnapHelper.attachToRecyclerView(selectTemplateRecyclerView)


        chooseLanguageSheetLabel.setFactory(factory)
        chooseLanguageSheetLabel.setCurrentText("Choose Language")
        val `in` = AnimationUtils.loadAnimation(context,
                android.R.anim.fade_in)
        val out = AnimationUtils.loadAnimation(context,
                android.R.anim.fade_out)
        chooseLanguageSheetLabel.inAnimation = `in`
        chooseLanguageSheetLabel.outAnimation = out
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                arrowUp.rotation = slideOffset * -180
                flagImage.alpha = (1 - slideOffset).toFloat()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {

                    chooseLanguageSheetLabel.setText("Choose Language")
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    selectedLanguage?.let {
                        chooseLanguageSheetLabel.setText(it.displayLanguage)
                    } ?: run {
                        chooseLanguageSheetLabel.setText("Choose Language")
                    }
                }
            }
        })
        bottomSheetBehavior.isHideable = false

        flagImage.clipToOutline = true



        chooseTemplateAdapter.createListener = Consumer {
            viewModel.addBucketFromExistingTemplate(it)
            Navigation.findNavController(view).popBackStack()
        }



        selectTemplateRecyclerView.visibility = View.GONE

        filteredTemplates.addSource(viewModel.availableTemplates) {
            filteredTemplates.value = it.filter { t -> selectedLanguage?.let { t.language == selectedLanguage } ?: true }
        }

        filteredTemplates.addSource(adapter.getLanguage()) {
            val list = viewModel.availableTemplates.value
            filteredTemplates.value = list?.filter { t -> t.language == it }
        }

        filteredTemplates.observe(viewLifecycleOwner, Observer {
            chooseTemplateAdapter.submitList(it)
            progressBar2.visibility = View.GONE
            selectTemplateRecyclerView.visibility = View.VISIBLE

        })
    }

    private val factory = ViewSwitcher.ViewFactory {
        val t = TextView(context)
        t.setTextAppearance(R.style.TextAppearance_MyTheme_Subtitle1)
        t
    }

}


