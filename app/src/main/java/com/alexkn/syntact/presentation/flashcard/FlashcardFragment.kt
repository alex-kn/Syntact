package com.alexkn.syntact.presentation.flashcard

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.databinding.FlashcardFragmentBinding
import kotlinx.android.synthetic.main.flashcard_fragment.*
import kotlin.math.ceil


class FlashcardFragment : Fragment() {

    lateinit var viewModel: FlashcardViewModel

    lateinit var binding: FlashcardFragmentBinding

    var state = State.SOLVE

    override

    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.flashcard_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.flashcardViewModelFactory())
                .get(FlashcardViewModel::class.java)

        nextButton.isEnabled = false
        nextButton.setOnClickListener {
            if (State.SOLVE == state) {

                val solved = viewModel.checkSolution(solutionInput.text.toString().trim())
                if (solved) {
                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_success))
                } else {
                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_error))
                }

                solutionInput.text?.clear()
                solutionInputLayout.visibility = View.INVISIBLE
                solutionOutput.visibility = View.VISIBLE
                current.animate().translationYBy(-20f).alpha(.75f).setDuration(100).withEndAction {
                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_surface))
                    current.animate().translationYBy(20f).alpha(1f).setDuration(100).withEndAction {
                        nextButton.text = "Next"
                        state = State.SOLUTION
                    }.start()
                }.start()
            } else if (State.SOLUTION == state) {
                nextButton.text = "Solve"
                solutionInputLayout.visibility = View.VISIBLE
                solutionOutput.visibility = View.INVISIBLE
                viewModel.fetchNext()
                state = State.SOLVE
            }
        }

        val bucketId = FlashcardFragmentArgs.fromBundle(arguments!!).bucketId
        viewModel.init(bucketId)

        solutionInput.addTextChangedListener(SolutionTextWatcher())
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (solutionInputLayout.requestFocus()) {
            imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)
        }

        viewModel.bucket!!.observe(this, Observer {
            val progress = ceil(it.dueCount.toDouble() / (it.itemCount - it.disabledCount) * 100).toInt()
            binding.progressBar3.progress = progress
            binding.headerDue.text = it.dueCount.toString()
            binding.headerTotal.text = "/" + (it.itemCount - it.disabledCount).toString()

        })

        viewModel.translation.observe(this, Observer {
            it?.let { cto ->
                nextButton.isEnabled = true;
                loadTranslationProgress.visibility = View.GONE
                binding.currentClue = cto.clue!!.text
                binding.solutionOutput.text = it.solvableItem.text
            } ?: run {
                nextButton.isEnabled = false;
                loadTranslationProgress.visibility = View.VISIBLE
                binding.currentClue = ""
                binding.solutionOutput.text = ""
            }
        })


        backButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
            imm.hideSoftInputFromWindow(solutionInput.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    private fun updateFlashcards() {
        viewModel.fetchNext()
    }


    inner class SolutionTextWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable) {
//            if (!s.isBlank()) {
//                if (viewModel.checkSolution(s.toString().trim())) {
//                    s.clear()
//                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_success))
//
//                    current.animate().translationYBy(-20f).alpha(0.75f).setDuration(100).withEndAction {
//                        viewModel.fetchNext()
//                        current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_surface))
//                        current.animate().translationYBy(20f).alpha(1f).setDuration(100).start()
//                    }.start()
//                }
//            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }

    enum class State { SOLVE, SOLUTION }
}

