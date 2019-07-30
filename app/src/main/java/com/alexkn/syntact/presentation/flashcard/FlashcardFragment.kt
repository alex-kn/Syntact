package com.alexkn.syntact.presentation.flashcard

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.constraintlayout.motion.widget.MotionController
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.databinding.FlashcardFragmentBinding
import kotlinx.android.synthetic.main.flashcard_fragment.*


class FlashcardFragment : Fragment() {

    lateinit var viewModel: FlashcardViewModel

    lateinit var binding: FlashcardFragmentBinding

    lateinit var motionLayout: MotionLayout

    var one: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.flashcard_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupMotionlayout(view)
        viewModel = ViewModelProviders
                .of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.flashcardViewModelFactory())
                .get(FlashcardViewModel::class.java)

        motionLayout = binding.root.findViewById<MotionLayout>(R.id.motionLayout)

        motionLayout.setTransitionListener(TransitionListener())

        backButton.setOnClickListener { Navigation.findNavController(it).popBackStack() }

        nextButton.setOnClickListener{
            val solved = viewModel.checkSolution(solutionInput.text.toString().trim(), one)
            if (solved) {
                current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_success))

            } else {
                current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_error))
            }
            motionLayout.transitionToEnd()
            solutionInput.text?.clear()
        }

        val bucketId = FlashcardFragmentArgs.fromBundle(arguments!!).bucketId
        viewModel.init(bucketId)

        solutionInput.addTextChangedListener(SolutionTextWatcher())
        if (solutionInputLayout.requestFocus()) {
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(solutionInput, InputMethodManager.SHOW_IMPLICIT)
        }

        viewModel.bucket!!.observe(this, Observer { this.updateFlashcards() })

        viewModel.solvableTranslations[0].observe(this, Observer {
            it?.let {
                binding.currentClue = it.clue.text
            } ?: run {
                binding.currentClue = "Done for the day"
            }
        })
        viewModel.solvableTranslations[1].observe(this, Observer {
            it?.let {
                binding.nextClue = it.clue.text
            } ?: run {
                binding.nextClue = "Done for the day"
            }
        })
    }

    private fun updateFlashcards() {
        if (one) {
            one = !one
            viewModel.solvableTranslations[1].removeObservers(this)
            viewModel.solvableTranslations[1].observe(this, Observer {
                it?.let {
                    binding.currentClue = it.clue.text
                } ?: run {
                    binding.currentClue = "Done for the day"
                }
            })

            viewModel.solvableTranslations[0].removeObservers(this)
            viewModel.solvableTranslations[0].observe(this, Observer {
                it?.let {
                    binding.nextClue = it.clue.text
                } ?: run {
                    binding.nextClue = "Done for the day"
                }
            })
            viewModel.fetchNext(one)
        } else {
            one = !one
            viewModel.solvableTranslations[0].removeObservers(this)
            viewModel.solvableTranslations[0].observe(this, Observer {
                it?.let {
                    binding.currentClue = it.clue.text
                } ?: run {
                    binding.currentClue = "Done for the day"
                }
            })
            viewModel.solvableTranslations[1].removeObservers(this)
            viewModel.solvableTranslations[1].observe(this, Observer {
                it?.let {
                    binding.nextClue = it.clue.text
                } ?: run {
                    binding.nextClue = "Done for the day"
                }
            })
            viewModel.fetchNext(one)
        }

    }

    private fun setupMotionlayout(view: View) {
        val vto = view.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                vto.removeOnGlobalLayoutListener(this)
                val width = current.measuredWidth

                val nextStateConstraintSet = motionLayout.getConstraintSet(R.id.next_state)
                nextStateConstraintSet.constrainWidth(R.id.next, width)
                nextStateConstraintSet.constrainWidth(R.id.current, width)
                val currentStateConstraintSet = motionLayout.getConstraintSet(R.id.next_state)
                currentStateConstraintSet.constrainWidth(R.id.next, width)
            }
        })
    }

    inner class SolutionTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (!s.isBlank()) {
                if (viewModel.checkSolution(s.toString().trim(), one)) {
                    s.clear()
                    motionLayout.transitionToEnd()
                    current.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_success))
                }

            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    }

    inner class TransitionListener : TransitionAdapter() {

        override fun onTransitionChange(motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float) = handleTransition(motionLayout)

        override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int)  = handleTransition(motionLayout)

        private fun handleTransition(motionLayout: MotionLayout) {
            if (motionLayout.currentState == R.id.next_state) {
                motionLayout.post {
                    motionLayout.progress = 0f
                    updateFlashcards()
                }
            }
        }


    }
}

