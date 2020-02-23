package com.alexkn.syntact.presentation.deckcreation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.google.android.material.chip.Chip
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.deck_creation_fragment.*


class DeckCreationFragment : Fragment() {

    private lateinit var imm: InputMethodManager
    private lateinit var viewModel: DeckCreationViewModel
    private val keywords = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext());
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inflater.inflate(R.layout.deck_creation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.createTemplateViewModelFactory())
                .get(DeckCreationViewModel::class.java)

        if (keywordsInput.requestFocus()) imm.showSoftInput(keywordsInput, InputMethodManager.SHOW_IMPLICIT)
        keywordsInput.addTextChangedListener(KeywordInputWatcher())
        keywordsInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) onAddText()
            false
        }

        setupSuggestionList()

        createTemplateButton.setOnClickListener {
            viewModel.createDeck("Test")
            Navigation.findNavController(it).popBackStack()
        }
        addTextButton.setOnClickListener { onAddText() }
        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }

    }

    private fun setupSuggestionList() {
        val suggestionListAdapter = DeckCreationItemAdapter()
        suggestionList.adapter = suggestionListAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        suggestionList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        suggestionList.addItemDecoration(dividerItemDecoration)

        viewModel.suggestions.observe(viewLifecycleOwner, Observer(suggestionListAdapter::submitList))
    }

    private fun onAddText() {
        val text = keywordsInput.text.toString().trim()

        val chip = LayoutInflater.from(requireContext()).inflate(R.layout.deck_creation_input_chip, keywordsChipGroup, false) as Chip
        chip.text = text
        chip.isCheckable = false
        chip.setOnCloseIconClickListener(this::onCloseChip)
        keywordsChipGroup.addView(chip)
        keywordsInput.text.clear()

        viewModel.fetchSuggestions(chip.id, text)
        keywords[chip.id] = text
    }

    private fun onCloseChip(view: View) {
        val chip = view as Chip
        keywordsChipGroup.removeView(chip)

        viewModel.removeKeyword(chip.id)
        keywords.remove(chip.id)
    }

    inner class KeywordInputWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable) {
            addTextButton.isEnabled = !s.isBlank()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }
}
