package com.alexkn.syntact.presentation.deckboard

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.data.model.DeckDetail
import com.alexkn.syntact.data.model.SolvableTranslationCto
import com.alexkn.syntact.databinding.DeckBoardFragmentBinding
import kotlinx.android.synthetic.main.deck_board_fragment.*
import org.apache.commons.text.similarity.LevenshteinDistance
import kotlin.math.ceil


class DeckBoardFragment : Fragment() {

    private lateinit var viewModel: DeckBoardViewModel

    private lateinit var binding: DeckBoardFragmentBinding

    private lateinit var imm: InputMethodManager

    private var solving = true

    private var levenshteinDistance: LevenshteinDistance = LevenshteinDistance.getDefaultInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.deck_board_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.flashcardViewModelFactory())
                .get(DeckBoardViewModel::class.java)

        imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (solutionInputLayout.requestFocus()) imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)

        viewModel.init(DeckBoardFragmentArgs.fromBundle(arguments!!).deckId)

        viewModel.deck.observe(this, Observer(this::onDeckChanged))
        viewModel.translation.observe(this, Observer(this::onSolvableTranslationChanged))

        solutionInput.addTextChangedListener(SolutionTextWatcher())
        nextFab.setOnClickListener { onNext() }
        backButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
            imm.hideSoftInputFromWindow(solutionInput.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun onDeckChanged(deck: DeckDetail) {
        val progress = ceil((1 - deck.dueCount.toDouble() / (deck.itemCount)) * 100).toInt()
        binding.progressBar3.progress = progress
        binding.headerDue.text = deck.dueCount.toString()
        binding.headerTotal.text = "/" + (deck.itemCount).toString()
    }

    private fun onSolvableTranslationChanged(translation: SolvableTranslationCto?) {
        if (translation == null) {
            nextFab.visibility = View.GONE
            binding.currentClue = ""
            binding.solutionOutput.text = ""
        } else {
            binding.currentClue = translation.clue.text
            binding.solutionOutput.text = translation.solvableItem.text
            similarityBar.max = translation.solvableItem.text.length
            similarityBar.progress = 0
            percentageOutput.text = "0 %"
        }
    }

    private fun onNext() {
        if (solving) {
            val solved = viewModel.checkSolution(solutionInput.text.toString().trim())
            if (solved) {
                solutionInput.text?.clear()
                viewModel.fetchNext()
            } else {
                solving = false
                solutionInputLayout.visibility = View.INVISIBLE
                similarityBar.visibility = View.INVISIBLE
                percentageOutput.visibility = View.INVISIBLE
                solutionOutput.visibility = View.VISIBLE
                imm.hideSoftInputFromWindow(solutionInput.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } else {
            viewModel.fetchNext()
            solving = true
            solutionInputLayout.visibility = View.VISIBLE
            similarityBar.visibility = View.VISIBLE
            percentageOutput.visibility = View.VISIBLE
            solutionOutput.visibility = View.INVISIBLE
            if (solutionInputLayout.requestFocus()) imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    inner class SolutionTextWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable) {
            if (!s.isBlank()) {
                val attempt = s.toString().trim()
                val solution = viewModel.translation.value?.solvableItem?.text
                solution.let {
                    similarityBar.progress = similarityBar.max - levenshteinDistance.apply(attempt, solution) + 1
                    percentageOutput.text = (similarityBar.progress.toDouble() / similarityBar.max * 100).toString() + "%"
                }
                if (viewModel.peekSolution(attempt)) {
                    viewModel.checkSolution(attempt)
                    s.clear()
                    viewModel.fetchNext()
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }
}

