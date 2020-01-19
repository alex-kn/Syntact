package com.alexkn.syntact.presentation.deckboard

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.databinding.DeckBoardFragmentBinding
import kotlinx.android.synthetic.main.deck_board_fragment.*
import org.apache.commons.text.similarity.LevenshteinDistance
import kotlin.math.ceil


class DeckBoardFragment : Fragment() {

    private lateinit var viewModel: DeckBoardViewModel

    private lateinit var binding: DeckBoardFragmentBinding

    private lateinit var imm: InputMethodManager

    private var state = State.SOLVE

    private var dueCount: Int = 1

    private var animating = false

    private var levenshteinDistance: LevenshteinDistance = LevenshteinDistance.getDefaultInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.deck_board_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.flashcardViewModelFactory())
                .get(DeckBoardViewModel::class.java)

        imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        nextButton.isEnabled = false

        nextButton.setOnClickListener {
            if (State.SOLVE == state) {

                val solved = viewModel.checkSolution(solutionInput.text.toString().trim())
                if (solved) {
                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_success))
                } else {
                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_error))
                }
                animating = true
                current.animate().translationYBy(-20f).alpha(.75f).setDuration(100).withEndAction {
                    animating = false
                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_surface))
                    current.animate().translationYBy(20f).alpha(1f).setDuration(100).start()
                    solutionInput.text?.clear()
                    solutionInputLayout.visibility = View.INVISIBLE
                    similarityBar.visibility = View.INVISIBLE
                    solutionOutput.visibility = View.VISIBLE
                    nextButton.text = "Next"
                }.start()
                state = State.SOLUTION
                imm.hideSoftInputFromWindow(solutionInput.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
            } else if (State.SOLUTION == state) {
                nextButton.text = "Solve"
                solutionInputLayout.visibility = View.VISIBLE
                similarityBar.visibility = View.VISIBLE
                solutionOutput.visibility = View.INVISIBLE

                viewModel.fetchNext()
                state = State.SOLVE
                if (solutionInputLayout.requestFocus()) {
                    imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)
                }
            }
        }

        val bucketId = DeckBoardFragmentArgs.fromBundle(arguments!!).deckId
        viewModel.init(bucketId)

        solutionInput.addTextChangedListener(SolutionTextWatcher())
        if (solutionInputLayout.requestFocus()) {
            imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)
        }

        viewModel.bucket!!.observe(this, Observer {
            val progress = ceil((1 - it.dueCount.toDouble() / (it.itemCount)) * 100).toInt()
            binding.progressBar3.progress = progress
            binding.headerDue.text = it.dueCount.toString()
            dueCount = it.dueCount
            binding.headerTotal.text = "/" + (it.itemCount).toString()
            checkDone()
        })

        viewModel.translation.observe(this, Observer {
            it?.let { cto ->
                nextButton.isEnabled = true
                loadTranslationProgress.visibility = View.GONE
                doneOutput.visibility = View.GONE
                binding.currentClue = cto.clue!!.text
                binding.solutionOutput.text = it.solvableItem.text
                similarityBar.max = it.solvableItem.text.length
            } ?: run {
                nextButton.isEnabled = false
                loadTranslationProgress.visibility = if (dueCount > 0 && !animating) View.VISIBLE else View.GONE
                checkDone()
                binding.currentClue = ""
                binding.solutionOutput.text = ""
            }
        })


        backButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
            imm.hideSoftInputFromWindow(solutionInput.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun checkDone() {
        val done = dueCount == 0 && !animating
        if (done) loadTranslationProgress.visibility = View.GONE
        doneOutput.visibility = if (done) View.VISIBLE else View.GONE
        solutionInputLayout.visibility = if (done) View.GONE else View.VISIBLE
        similarityBar.visibility = if (done) View.GONE else View.VISIBLE
        if (done) imm.hideSoftInputFromWindow(solutionInput.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    inner class SolutionTextWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable) {
            if (!s.isBlank()) {
                val attempt = s.toString().trim()
                val solution = viewModel.translation.value?.solvableItem?.text
                solution.let { similarityBar.progress = similarityBar.max - levenshteinDistance.apply(attempt, solution) + 1 }
                if (viewModel.peekSolution(attempt)) {
                    s.clear()
                    solutionInputLayout.visibility = View.INVISIBLE
                    similarityBar.visibility = View.INVISIBLE
                    solutionOutput.visibility = View.VISIBLE
                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_success))

                    animating = true
                    current.animate().translationYBy(-20f).alpha(0.75f).setDuration(100).withEndAction {
                        animating = false
                        viewModel.fetchNext()
                        solutionInputLayout.visibility = View.VISIBLE
                        similarityBar.visibility = View.VISIBLE
                        solutionOutput.visibility = View.INVISIBLE
                        current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_surface))
                        current.animate().translationYBy(20f).alpha(1f).setDuration(100).start()
                        if (solutionInputLayout.requestFocus()) {
                            imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)
                        }
                    }.start()
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }

    enum class State { SOLVE, SOLUTION }
}

