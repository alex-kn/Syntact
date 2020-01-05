package com.alexkn.syntact.presentation.createtemplate

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.app.TAG
import com.google.android.material.chip.Chip
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.bucket_details.*
import kotlinx.android.synthetic.main.create_template_fragment.*


class CreateTemplateFragment : Fragment() {

    private lateinit var imm: InputMethodManager
    private lateinit var viewModel: CreateTemplateViewModel
    private val items = mutableMapOf<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext());
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inflater.inflate(R.layout.create_template_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.createTemplateViewModelFactory())
                .get(CreateTemplateViewModel::class.java)

        if (keywordsInput.requestFocus()) imm.showSoftInput(keywordsInput, InputMethodManager.SHOW_IMPLICIT)
        keywordsInput.addTextChangedListener(KeywordInputWatcher())
        keywordsInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) onAddText()
            false
        }

        val suggestionListAdapter = SuggestionListAdapter()
        suggestionList.adapter = suggestionListAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        suggestionList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        suggestionList.addItemDecoration(dividerItemDecoration)

        viewModel.suggestions.observe(viewLifecycleOwner, Observer(suggestionListAdapter::submitList))

        createTemplateButton.setOnClickListener(this::onCreateTemplate)
        addTextButton.setOnClickListener { onAddText() }

    }

    private fun onAddText() {
        val text = keywordsInput.text.toString().trim()
        viewModel.fetchSuggestions(text)

        val translation = "NYI"

        val chip = LayoutInflater.from(requireContext()).inflate(R.layout.input_chip, keywordsChipGroup, false) as Chip
        chip.text = text
        items[text] = translation
        chip.setOnCloseIconClickListener(this::onCloseChip)
        keywordsChipGroup.addView(chip)
        keywordsInput.text.clear()
    }

    private fun onCloseChip(view: View) {

        val chip = view as Chip
        keywordsChipGroup.removeView(chip)
        items.remove(chip.text)
    }

    private fun onCreateTemplate(view: View) {

        Log.i(TAG, "CreateTemplateFragment: Input text: $items")
    }

    inner class KeywordInputWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable) {
//            addTextButton.visibility = if (s.isBlank()) View.INVISIBLE else View.VISIBLE
            addTextButton.isEnabled = !s.isBlank()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }


}
