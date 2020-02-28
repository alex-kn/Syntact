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
import android.view.animation.AccelerateDecelerateInterpolator
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
    private var done = false

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
        nextButton.setOnClickListener { onNext() }
        backButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
            imm.hideSoftInputFromWindow(solutionInput.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun onDeckChanged(deck: DeckDetail) {
        binding.headerDue.text = deck.dueCount.toString()
        binding.headerTotal.text = "/" + (deck.itemCount).toString()
        done = deck.dueCount == 0
    }

    private fun onDone() {
        listOf(textView4, similarityBar, solutionInputLayout, solutionOutput, scoreOutput, nextButton).forEach { v -> v.visibility = View.INVISIBLE }
        doneOutput.visibility = View.VISIBLE
    }

    private fun onSolvableTranslationChanged(translation: SolvableTranslationCto?) {
        if (translation == null) {
            nextButton.visibility = View.GONE
            binding.currentClue = ""
            binding.solutionOutput.text = ""
        } else {
            binding.currentClue = translation.clue.text
            binding.solutionOutput.text = translation.solvableItem.text
        }
    }

    private fun onNext() {
        if (solving) {
            solving = false
            val score = viewModel.checkSolution(solutionInput.text.toString().trim(), peek = false)
            solutionInput.text?.clear()
            onSolved(score)
            solutionInputLayout.visibility = View.INVISIBLE
            similarityBar.visibility = View.INVISIBLE
            scoreOutput.visibility = View.INVISIBLE
            solutionOutput.visibility = View.VISIBLE
            imm.hideSoftInputFromWindow(solutionInput.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } else {
            if (done) onDone() else {
                solving = true
                viewModel.fetchNext()
                solutionInputLayout.visibility = View.VISIBLE
                similarityBar.visibility = View.VISIBLE
                scoreOutput.visibility = View.VISIBLE
                solutionOutput.visibility = View.INVISIBLE
                if (solutionInputLayout.requestFocus()) imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }


    private fun onSolved(score: Double) {

        scoreAnimation.end()
        val view = if (score > 0.9) solvedIndicator else failedIndicator
        view.visibility = View.VISIBLE
        val scaleAnimX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f, 1f, 1f, 1f, 1f)
        val scaleAnimY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f, 1f, 1f, 1f, 1f)
        val alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 1f, 1f, 0f)
        scaleAnimX.duration = 1000
        scaleAnimY.duration = 1000
        alphaAnim.duration = 1000
        scoreAnimation = AnimatorSet()
        scoreAnimation.interpolator = AccelerateDecelerateInterpolator()
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
            if (score == 1.0) onNext()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }
}

