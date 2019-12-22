package com.alexkn.syntact.presentation.flashcard

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
import com.alexkn.syntact.databinding.FlashcardFragmentBinding
import kotlinx.android.synthetic.main.flashcard_fragment.*
import org.apache.commons.text.similarity.LevenshteinDistance
import kotlin.math.ceil


class FlashcardFragment : Fragment() {

    lateinit var viewModel: FlashcardViewModel

    lateinit var binding: FlashcardFragmentBinding

    private var state = State.SOLVE

    var dueCount: Int = 1

    var levenshteinDistance: LevenshteinDistance = LevenshteinDistance.getDefaultInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.flashcard_fragment, container, false)
        return binding.root
    }

    private lateinit var imm: InputMethodManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.flashcardViewModelFactory())
                .get(FlashcardViewModel::class.java)

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
                current.animate().translationYBy(-20f).alpha(.75f).setDuration(100).withEndAction {
                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_surface))
                    current.animate().translationYBy(20f).alpha(1f).setDuration(100).start()

                    solutionInput.text?.clear()
                    solutionInputLayout.visibility = View.INVISIBLE
                    solutionOutput.visibility = View.VISIBLE
                    nextButton.text = "Next"
                }.start()
                state = State.SOLUTION
                imm.hideSoftInputFromWindow(solutionInput.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
            } else if (State.SOLUTION == state) {
                nextButton.text = "Solve"
                solutionInputLayout.visibility = View.VISIBLE
                solutionOutput.visibility = View.INVISIBLE

                viewModel.fetchNext()
                state = State.SOLVE
                if (solutionInputLayout.requestFocus()) {
                    imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)
                }
            }
        }

        val bucketId = FlashcardFragmentArgs.fromBundle(arguments!!).bucketId
        viewModel.init(bucketId)

        solutionInput.addTextChangedListener(SolutionTextWatcher())
        if (solutionInputLayout.requestFocus()) {
            imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)
        }

        viewModel.bucket!!.observe(this, Observer {
            val progress = ceil(it.dueCount.toDouble() / (it.itemCount - it.disabledCount) * 100).toInt()
            binding.progressBar3.progress = progress
            binding.headerDue.text = it.dueCount.toString()
            dueCount = it.dueCount
            if (dueCount == 0) {
                loadTranslationProgress.visibility = View.GONE
                doneOutput.visibility = View.VISIBLE
            }
            binding.headerTotal.text = "/" + (it.itemCount - it.disabledCount).toString()

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
                loadTranslationProgress.visibility = if (dueCount > 0) View.VISIBLE else View.GONE
                doneOutput.visibility = if (dueCount > 0 || state != State.SOLUTION) View.GONE else View.VISIBLE
                binding.currentClue = ""
                binding.solutionOutput.text = ""
            }
        })


        backButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
            imm.hideSoftInputFromWindow(solutionInput.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)

        }
    }

    private fun updateFlashcards() {
        viewModel.fetchNext()
    }


    inner class SolutionTextWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable) {
            if (!s.isBlank()) {
                val attempt = s.toString().trim()
                val solution = viewModel.translation.value?.solvableItem?.text
                solution.let { similarityBar.progress = similarityBar.max -  levenshteinDistance.apply(attempt, solution) }
                if (viewModel.checkSolution(attempt)) {
                    s.clear()
                    solutionInputLayout.visibility = View.INVISIBLE
                    solutionOutput.visibility = View.VISIBLE
                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_success))

                    current.animate().translationYBy(-20f).alpha(0.75f).setDuration(100).withEndAction {
                        viewModel.fetchNext()
                        solutionInputLayout.visibility = View.VISIBLE
                        solutionOutput.visibility = View.INVISIBLE
                        current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_surface))
                        current.animate().translationYBy(20f).alpha(1f).setDuration(100).start()
                    }.start()
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }

    enum class State { SOLVE, SOLUTION }
}

