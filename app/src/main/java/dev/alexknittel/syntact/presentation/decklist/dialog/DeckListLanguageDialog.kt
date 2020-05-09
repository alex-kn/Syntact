package dev.alexknittel.syntact.presentation.decklist.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.alexknittel.syntact.R
import kotlinx.android.synthetic.main.deck_list_language_dialog.*
import java.util.*

class DeckListLanguageDialog : DialogFragment() {

    private lateinit var list: List<Pair<Locale, Locale>>
    private lateinit var onChooseLanguage: (Locale) -> Unit

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 32))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.deck_list_language_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        deckListLanguageList.layoutManager = LinearLayoutManager(requireContext())
        deckListLanguageList.adapter = DeckListLanguageDialogItemAdaper(list, onChooseLanguage)
        deckListLanguageCancelButton.setOnClickListener { dismiss() }
    }

    fun bindTo(list: List<Pair<Locale, Locale>>, onChooseLanguage: (Locale) -> Unit) {
        this.list = list
        this.onChooseLanguage = onChooseLanguage
    }
}