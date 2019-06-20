package com.alexkn.syntact.presentation.flashcard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.databinding.FlashcardFragmentBinding
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto

class FlashcardFragment : Fragment() {

    lateinit var viewModel: FlashcardViewModel

    lateinit var binding: FlashcardFragmentBinding

    lateinit var motionLayout: MotionLayout

    var one: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.flashcard_fragment, container, false)
        viewModel = ViewModelProviders
                .of(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.flashcardViewModelFactory())
                .get(FlashcardViewModel::class.java)

        motionLayout = binding.root.findViewById<MotionLayout>(R.id.motionLayout)

        motionLayout.setTransitionListener(TransitionListener())


        val bucketId = FlashcardFragmentArgs.fromBundle(arguments!!).bucketId
        viewModel.init(bucketId)

        viewModel.translations!!.observe(this, Observer<List<SolvableTranslationCto>> { translations ->
            if (translations.size < 4) {
                viewModel.triggerPhrasesFetch()
            }
        })

        val editText = binding.root.findViewById<EditText>(R.id.solutionInput)
        editText.addTextChangedListener(SolutionTextWatcher())

        viewModel.bucket!!.observe(this, Observer { this.updateFlashcards() })

        viewModel.solvableTranslations[0].observe(this, Observer { binding.currentClue = it.clue.text })
        viewModel.solvableTranslations[1].observe(this, Observer { binding.nextClue = it.clue.text })
        return binding.root
    }

    private fun updateFlashcards() {
        if (one) {
            one = !one
            viewModel.solvableTranslations[1].removeObservers(this)
            viewModel.solvableTranslations[1].observe(this, Observer { binding.currentClue = it.clue.text })

            viewModel.solvableTranslations[0].removeObservers(this)
            viewModel.solvableTranslations[0].observe(this, Observer { binding.nextClue = it.clue.text })
            viewModel.fetchNext(one)
        } else {
            one = !one
            viewModel.solvableTranslations[0].removeObservers(this)
            viewModel.solvableTranslations[0].observe(this, Observer { binding.currentClue = it.clue.text })
            viewModel.solvableTranslations[1].removeObservers(this)
            viewModel.solvableTranslations[1].observe(this, Observer { binding.nextClue = it.clue.text })
            viewModel.fetchNext(one)
        }

    }

    inner class SolutionTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (!s.isBlank()) {
                if (viewModel.checkSolution(s.toString(), one)) {
                    s.clear()
                    motionLayout.transitionToEnd()
                }

            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    }

    inner class TransitionListener : TransitionAdapter() {

        override fun onTransitionChange(motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float) = handleTransition(motionLayout)

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

