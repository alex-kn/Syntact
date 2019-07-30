package com.alexkn.syntact.presentation.createbucket

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.restservice.Template
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bucket_create_fragment.*
import kotlinx.android.synthetic.main.language_sheet.*
import java.util.*
import java.util.function.Consumer
import android.widget.TextView
import android.widget.ViewSwitcher
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.alexkn.syntact.R
import com.alexkn.syntact.app.TAG
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlin.math.round


class CreateBucketFragment : Fragment() {

    private lateinit var viewModel: CreateBucketViewModel

    private var selectedLanguage: Locale? = null

    private var filteredTemplates: MediatorLiveData<List<Template>> = MediatorLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.alexkn.syntact.R.layout.bucket_create_fragment, container, false)
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

        createButton.visibility = View.GONE
        adapter.getLanguage().observe(viewLifecycleOwner, Observer { locale ->
            selectedLanguage = locale
            chooseLanguageSheetLabel.setText(selectedLanguage!!.displayLanguage)
            createButton.visibility = View.VISIBLE
            val resId = context!!.resources.getIdentifier(locale.language , "drawable", context!!.packageName)
            flagImage.setImageResource(resId)
            Handler().postDelayed({
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }, 300)
        })


        val templateLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        selectTemplateRecyclerView.layoutManager = templateLayoutManager

        val chooseTemplateAdapter = ChooseTemplateAdapter()
        selectTemplateRecyclerView.adapter = chooseTemplateAdapter
        val linearSnapHelper1 = LinearSnapHelper()

        linearSnapHelper1.attachToRecyclerView(selectTemplateRecyclerView)


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
                arrowUp.rotation = slideOffset * -180;
                flagImage.alpha = (0.5 - slideOffset * 0.5).toFloat()
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

        createButton.setOnClickListener {
//            viewModel.addBucketFromNewTemplate(selectedLanguage!!, listOf("Brücke", "Bier", "Auto")) TODO implement
        }

        selectTemplateRecyclerView.visibility = View.GONE
        arrowLeftImageView.visibility = View.GONE
        arrowRightImageView.visibility = View.GONE

        filteredTemplates.addSource(viewModel.availableTemplates, Observer {
            filteredTemplates.value = it.filter { t -> selectedLanguage?.let { t.language == selectedLanguage } ?: true }
        })

        filteredTemplates.addSource(adapter.getLanguage(), Observer {
            val list = viewModel.availableTemplates.value
            filteredTemplates.value = list?.filter { t -> t.language == it }
        })

        filteredTemplates.observe(viewLifecycleOwner, Observer {
            chooseTemplateAdapter.submitList(it)
            progressBar2.visibility = View.GONE
            selectTemplateRecyclerView.visibility = View.VISIBLE
            arrowLeftImageView.visibility = View.VISIBLE
            arrowRightImageView.visibility = View.VISIBLE
        })
    }

    private val factory = ViewSwitcher.ViewFactory {
        val t = TextView(context)

        t.setTextAppearance(R.style.TextAppearance_MyTheme_Subtitle1)
        t
    }

}


