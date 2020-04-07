package com.alexkn.syntact.presentation.deckcreation.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.alexkn.syntact.R
import com.alexkn.syntact.service.Suggestion
import kotlinx.android.synthetic.main.deck_creation_detail_dialog.*

class DeckCreationDetailDialog : DialogFragment() {

    lateinit var onDelete: (Suggestion) -> Unit
    lateinit var onSave: (Suggestion) -> Unit

    private lateinit var suggestion: Suggestion


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.deck_creation_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        deckCreationDetailDeleteButton.setOnClickListener {
            onDelete.invoke(suggestion)
            dialog?.dismiss()
        }
        deckCreationDetailAddButton.setOnClickListener {
            onSave.invoke(suggestion)
            dialog?.dismiss()
        }
        deckCreationDetailCancelButton.setOnClickListener { dialog?.dismiss() }

        topItemOutput.text = suggestion.src
        bottomItemOutput.text = suggestion.dest
    }

    fun bindTo(suggestion: Suggestion) {
        this.suggestion = suggestion
    }
}