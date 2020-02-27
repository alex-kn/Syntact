package com.alexkn.syntact.presentation.deckboard

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import kotlin.math.roundToInt


class DeckBoardFragment : Fragment() {

    private lateinit var viewModel: DeckBoardViewModel
    private lateinit var binding: DeckBoardFragmentBinding
    private lateinit var imm: InputMethodManager

    private var solving = true

    private var scoreAnimation = AnimatorSet()

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
        viewModel.currentScore.observe(this, Observer(this::onCurrentScoreChanged))
        viewModel.maxScore.observe(this, Observer(this::onMaxScoreChanged))

        solutionInput.addTextChangedListener(SolutionTextWatcher())
        nextFab.setOnClickListener { onNext() }
        backButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
            imm.hideSoftInputFromWindow(solutionInput.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun onDeckChanged(deck: DeckDetail) {
        binding.headerDue.text = deck.dueCount.toString()
        binding.headerTotal.text = "/" + (deck.itemCount).toString()
        if (deck.dueCount == 0) {
            listOf(textView4, similarityBar, solutionInputLayout, solutionOutput, scoreOutput).forEach { v -> v.visibility = View.INVISIBLE }
            doneOutput.visibility = View.VISIBLE
        }
    }

    private fun onSolvableTranslationChanged(translation: SolvableTranslationCto?) {
        if (translation == null) {
            nextFab.visibility = View.GONE
            binding.currentClue = ""
            binding.solutionOutput.text = ""
        } else {
            binding.currentClue = translation.clue.text
            binding.solutionOutput.text = translation.solvableItem.text
        }
    }

    private fun onNext() {
        if (solving) {
            val score = viewModel.checkSolution(solutionInput.text.toString().trim(), peek = false)
            val solved = score >= 0.9
            if (solved) {
                solutionInput.text?.clear()
                onSolved((score * 100).roundToInt())
            } else {
                solving = false
                solutionInputLayout.visibility = View.INVISIBLE
                similarityBar.visibility = View.INVISIBLE
                scoreOutput.visibility = View.INVISIBLE
                solutionOutput.visibility = View.VISIBLE
                imm.hideSoftInputFromWindow(solutionInput.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } else {
            viewModel.fetchNext()
            solving = true
            solutionInputLayout.visibility = View.VISIBLE
            similarityBar.visibility = View.VISIBLE
            scoreOutput.visibility = View.VISIBLE
            solutionOutput.visibility = View.INVISIBLE
            if (solutionInputLayout.requestFocus()) imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)
        }
    }


    private fun onSolved(score: Int) {

        scoreAnimation.end()
        scoreChangeOutput.visibility = View.VISIBLE
        val scaleAnimX = ObjectAnimator.ofFloat(scoreChangeOutput, "scaleX", 1f, 1.2f, 1f, 1f, 1f, 1f, 1f)
        val scaleAnimY = ObjectAnimator.ofFloat(scoreChangeOutput, "scaleY", 1f, 1.2f, 1f, 1f, 1f, 1f, 1f)
        val alphaAnim = ObjectAnimator.ofFloat(scoreChangeOutput, "alpha", 1f, 1f, 0f)
        scaleAnimX.duration = 1000
        scaleAnimY.duration = 1000
        alphaAnim.duration = 1000
        scoreAnimation = AnimatorSet()
        scoreAnimation.playTogether(scaleAnimX, scaleAnimY, alphaAnim)
        scoreAnimation.start()
    }

    private fun onCurrentScoreChanged(currentScore: Int) {

        similarityBar.progress = currentScore
        val score = viewModel.scoreRatio * 100
        if (!score.isNaN()) scoreOutput.text = score.roundToInt().toString() + " %"
    }

    private fun onMaxScoreChanged(maxScore: Int) {

        similarityBar.max = maxScore
        val score = viewModel.scoreRatio * 100
        if (!score.isNaN()) scoreOutput.text = score.roundToInt().toString() + " %"
    }

    inner class SolutionTextWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable) {
            val score = viewModel.checkSolution(s.toString().trim(), peek = true)
            if (!s.isBlank() && score == 1.0) {
                s.clear()
                viewModel.checkSolution(s.toString().trim(), peek = false)
                onSolved((score * 100).roundToInt())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }
}

