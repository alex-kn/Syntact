package com.alexkn.syntact.presentation.deckcreation.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import com.alexkn.syntact.R
import com.alexkn.syntact.service.Suggestion
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.deck_creation_detail_dialog.*
import java.util.*

class DeckCreationDetailDialog : DialogFragment() {

    lateinit var onDelete: (Suggestion) -> Unit
    lateinit var onSave: (Map<Locale, Set<String>>) -> Unit

    private lateinit var suggestion: Suggestion

    private val keywordsToAdd = mutableMapOf<Locale, MutableSet<String>>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 32))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.deck_creation_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        deckCreationDetailDeleteButton.setOnClickListener {
            onDelete.invoke(suggestion)
            dialog?.dismiss()
        }
        deckCreationDetailAddButton.setOnClickListener {
            onSave.invoke(keywordsToAdd)
            dialog?.dismiss()
        }
        deckCreationDetailCancelButton.setOnClickListener { dialog?.dismiss() }
        deckCreationDetailAddButton.isEnabled = false
        setupSentence(suggestion.src, suggestion.srcLang, topItemLayout)
        setupSentence(suggestion.dest, suggestion.destLang, bottomItemLayout)
    }

    fun bindTo(suggestion: Suggestion) {
        this.suggestion = suggestion
    }

    private fun setupSentence(text: String, locale: Locale, layout: ViewGroup) {
        keywordsToAdd[locale] = mutableSetOf()
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(6, 3, 6, 3)
        text.split(" ").forEach {
            val materialTextView = MaterialTextView(requireContext())
            materialTextView.layoutParams = layoutParams
            materialTextView.setTextAppearance(R.style.TextAppearance_MyTheme_Headline5)
            materialTextView.text = it

            layout.addView(materialTextView)
            materialTextView.setOnClickListener { v ->
                val textView = v as MaterialTextView
                if (textView.isActivated) {
                    keywordsToAdd[locale]!!.remove(it)
                    materialTextView.setTextColor(resources.getColor(R.color.color_on_background, null))
                } else {
                    keywordsToAdd[locale]!!.add(it)
                    materialTextView.setTextColor(resources.getColor(R.color.color_secondary, null))
                }
                materialTextView.isActivated = !materialTextView.isActivated
                deckCreationDetailAddButton.isEnabled = keywordsToAdd.values.flatten().isNotEmpty()
            }

        }
    }

}