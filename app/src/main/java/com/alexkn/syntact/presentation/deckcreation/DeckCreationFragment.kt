package com.alexkn.syntact.presentation.deckcreation

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.deck_creation_fragment.*
import kotlinx.android.synthetic.main.deck_creation_fragment.topLayout
import kotlinx.android.synthetic.main.deck_list_fragment.*
import kotlin.math.max


class DeckCreationFragment : Fragment() {

    private lateinit var imm: InputMethodManager
    private lateinit var viewModel: DeckCreationViewModel
    private val keywords = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
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

        viewModel.suggestionLang.observe(viewLifecycleOwner, Observer {
            val resId = resources.getIdentifier(it.language, "drawable", requireContext().packageName)
            val drawable = ResourcesCompat.getDrawable(resources, resId, null)
            deckCreationFlagView.setImageDrawable(drawable)
        })

        deckCreationFlagView.clipToOutline = true
        deckCreationFlagContainer.setOnClickListener { viewModel.switchSuggestionLang() }

        deckCreationNameInput.setText(viewModel.defaultDeckName)
        deckCreationNameInput.addTextChangedListener(NameInputWatcher())

        setupBackdrop()
        setupSuggestionList()

        finishDeckFab.setOnClickListener {
            viewModel.createDeck(deckCreationNameInput.text.toString())
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            Navigation.findNavController(it).popBackStack()
        }

        addTextButton.setOnClickListener { onAddText() }
        backButton.setOnClickListener {
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            Navigation.findNavController(it).popBackStack()
        }

    }

    private fun setupBackdrop() {
        val sheet = BottomSheetBehavior.from(deckCreationContentLayout)
        sheet.isFitToContents = false
        sheet.isHideable = false
        expand(sheet)

        deckCreationBackdropButton.setOnClickListener {
            if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) collapse(sheet)
            else expand(sheet)
        }
        topLayout.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) collapse(sheet) }
        deckCreationContentLayout.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_COLLAPSED) expand(sheet) }
    }

    private fun setupSuggestionList() {
        val suggestionListAdapter = DeckCreationItemAdapter()
        suggestionList.adapter = suggestionListAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        suggestionList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        suggestionList.addItemDecoration(dividerItemDecoration)

        suggestionList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(languagesList, dx, dy)
                if (dy > 0 && finishDeckFab.isShown) {
                    finishDeckFab.hide()
                } else if (dy < 0 && !finishDeckFab.isShown) {
                    finishDeckFab.show()
                }
            }
        })

        viewModel.suggestions.observe(viewLifecycleOwner, Observer { suggestions ->
            numberOfCardsOutput.text = suggestions.values.flatten().size.toString()
            suggestionListAdapter.submitList(suggestions.toSortedMap().values.flatten())
            var emptyChips = mutableListOf<Chip>()
            suggestions.filter { it.value.isEmpty() }.keys.forEach {
                val chip = keywordsChipGroup.findViewById<Chip>(it)
                val color = ContextCompat.getColor(requireContext(), R.color.color_error)
                chip.chipBackgroundColor = ColorStateList.valueOf(color)
            }
        })
    }

    private fun onAddText() {
        val text = keywordsInput.text.toString().trim()

        val chip = LayoutInflater.from(requireContext()).inflate(R.layout.deck_creation_input_chip, keywordsChipGroup, false) as Chip
        chip.text = text
        chip.isCheckable = false

        val src = ResourcesCompat.getDrawable(resources, viewModel.suggestionFlag, null)!!.toBitmap()
        val roundedSrc = RoundedBitmapDrawableFactory.create(resources, src)
        roundedSrc.cornerRadius = max(src.width, src.height) / 2f
        roundedSrc.isCircular = true
        chip.chipIcon = roundedSrc
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

    private fun expand(sheetBehavior: BottomSheetBehavior<LinearLayout>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        deckCreationBackdropButton.rotation = 180f

        header.animate().alpha(0.5f).setDuration(100).withEndAction {
            header.text = "Type anything that comes to mind"
            header.animate().alpha(1f).setDuration(100).start()
        }.start()

        deckCreationBackdropButton.animate().rotation(270f).alpha(0.5f).setDuration(100).withEndAction {
            deckCreationBackdropButton.setImageResource(R.drawable.ic_baseline_build_24)
            deckCreationBackdropButton.animate().rotation(360f).alpha(1f).setDuration(100).start()
        }.start()
        finishDeckFab.show()
    }

    private fun collapse(sheetBehavior: BottomSheetBehavior<LinearLayout>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        deckCreationBackdropButton.rotation = 0f

        header.animate().alpha(0.5f).setDuration(100).withEndAction {
            header.text = "Tap here to view generated cards"
            header.animate().alpha(1f).setDuration(100).start()
        }.start()

        deckCreationBackdropButton.animate().rotation(90f).alpha(0.5f).setDuration(100).withEndAction {
            deckCreationBackdropButton.setImageResource(R.drawable.ic_baseline_list_24)
            deckCreationBackdropButton.animate().rotation(180f).alpha(1f).setDuration(100).start()
        }.start()
        finishDeckFab.hide()
    }

    inner class KeywordInputWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable) {
            addTextButton.isEnabled = !s.isBlank()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }

    inner class NameInputWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }
}
