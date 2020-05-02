package com.alexkn.syntact.presentation.deckcreation

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.app.general.config.ApplicationComponentProvider
import com.alexkn.syntact.presentation.common.animateIn
import com.alexkn.syntact.presentation.common.animateOut
import com.alexkn.syntact.presentation.common.flagDrawableOf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.deck_creation_bottom_sheet.*
import kotlinx.android.synthetic.main.deck_creation_fragment.*
import kotlinx.android.synthetic.main.deck_creation_fragment.topLayout
import kotlinx.android.synthetic.main.deck_list_fragment.*
import java.util.*
import kotlin.math.max


class DeckCreationFragment : Fragment() {

    private val args: DeckCreationFragmentArgs by navArgs()

    private lateinit var dialog: AlertDialog
    private lateinit var sheet: BottomSheetBehavior<LinearLayout>

    private lateinit var imm: InputMethodManager
    private lateinit var viewModel: DeckCreationViewModel
    private val keywords = mutableMapOf<Int, String>()

    private var activeKeywordInput: EditText? = null
    private val suggestionLang: Locale
        get() = if (leftInputActive) viewModel.deckLang.value!! else viewModel.userLang.value!!

    private var leftInputActive = true

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

        viewModel.setLang(args.lang)

        deckCreationRightLangFlag.clipToOutline = true
        deckCreationLeftLangFlag.clipToOutline = true

        viewModel.userLang.observe(viewLifecycleOwner, Observer {
            keywordsInputRight.hint = it?.displayLanguage
            deckCreationRightLangOutput.text = it?.displayLanguage
            it?.let { deckCreationRightLangFlag.setImageDrawable(resources.flagDrawableOf(it)) }
        })

        keywordsInputLeft.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) onAddText(v as EditText)
            false
        }
        keywordsInputRight.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) onAddText(v as EditText)
            false
        }


        keywordsInputLeft.addTextChangedListener { addTextButton.isEnabled = !(it?.isBlank() ?: true) }
        keywordsInputRight.addTextChangedListener { addTextButton.isEnabled = !(it?.isBlank() ?: true) }

        keywordsInputLeft.setOnFocusChangeListener { v, _ ->
            val editText = v as EditText
            activeKeywordInput = editText
            leftInputActive = true
        }
        keywordsInputRight.setOnFocusChangeListener { v, _ ->
            val editText = v as EditText
            activeKeywordInput = editText
            leftInputActive = false
        }

        deckCreationNameInput.setText(resources.getString(R.string.deck_creation_default_name))
        viewModel.setDeckName("My New Deck")

        buildLanguageDialog()

        viewModel.deckLang.observe(viewLifecycleOwner, Observer {
            it?.let {
                deckCreationLanguageOutput.text = it.displayLanguage
                keywordsInputLeft.hint = it.displayLanguage
                deckCreationLeftLangOutput.text = it.displayLanguage
                deckCreationLeftLangFlag.setImageDrawable(resources.flagDrawableOf(it))
            }
        })
        viewModel.deckName.observe(viewLifecycleOwner, Observer {
            it?.let {
                deckCreationHeaderExpanded.text = it
            }
        })
        deckCreationNameInput.addTextChangedListener { viewModel.setDeckName(it.toString()) }

        deckCreationCardsPerDayInput.setText(viewModel.defaultNewCardsPerDay.toString())
        deckCreationLanguageOutput.setOnClickListener { dialog.show() }

        setupBackdrop()
        setupSuggestionList()

        finishDeckFab.setOnClickListener {
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            if (viewModel.suggestions.value?.values.isNullOrEmpty()) {
                Snackbar.make(requireView(), "Please add cards to your deck", Snackbar.LENGTH_SHORT).setAnchorView(R.id.finishDeckFab).show()
            } else {
                collapse(sheet)
                deckCreationScrollView.fullScroll(View.FOCUS_DOWN)
            }
        }
        deckCreationCreateDeckButton.background.colorFilter = PorterDuffColorFilter(resources.getColor(R.color.color_secondary, null), PorterDuff.Mode.SRC_ATOP)

        deckCreationCreateDeckButton.setOnClickListener {
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            when {
                viewModel.deckName.value.isNullOrBlank() -> {
                    Snackbar.make(requireView(), "Please enter a name for your deck.", Snackbar.LENGTH_SHORT).setAnchorView(R.id.deckCreationHeaderLayout).show()
                    deckCreationNameInput.requestFocus()
                }
                viewModel.suggestions.value?.values.isNullOrEmpty() -> {
                    expand(sheet)
                    Snackbar.make(requireView(), "Please add cards to your deck", Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.createDeck(deckCreationCardsPerDayInput.text.toString())
                    Snackbar.make(activity!!.findViewById(R.id.nav_host_fragment), "${viewModel.deckName.value} created.", Snackbar.LENGTH_SHORT).show()
                    Navigation.findNavController(it).popBackStack()
                }
            }
        }

        addTextButton.setOnClickListener { activeKeywordInput?.let { onAddText(it) } }
        backButton.setOnClickListener {
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            Navigation.findNavController(it).popBackStack()
        }

        finishDeckFab.show()
    }


    private fun setupBackdrop() {
        sheet = BottomSheetBehavior.from(deckCreationContentLayout)
        sheet.isFitToContents = false
        sheet.isHideable = false
        expand(sheet)

        deckCreationBackdropButton.setOnClickListener {
            if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) collapse(sheet)
            else expand(sheet)
        }
        topLayout.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) collapse(sheet) }
        deckCreationHeaderLayout.setOnClickListener { if (sheet.state == BottomSheetBehavior.STATE_COLLAPSED) expand(sheet) }
    }

    private fun setupSuggestionList() {
        val suggestionListAdapter = DeckCreationItemAdapter()
        suggestionListAdapter.onDeleteListener = { viewModel.removeItem(it) }
        suggestionListAdapter.onSaveListener = { generateFromSuggestion(it) }
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
            suggestionListEmptyLabel.visibility = if (suggestions.values.firstOrNull().isNullOrEmpty()) View.VISIBLE else View.GONE
            suggestionListAdapter.submitList(suggestions.toSortedMap().values.flatten())
            suggestions.filter { it.value.isEmpty() }.keys.forEach {
                val chip = keywordsChipGroup.findViewById<Chip?>(it)
                val color = ContextCompat.getColor(requireContext(), R.color.color_error)
                chip?.chipBackgroundColor = ColorStateList.valueOf(color)
            }
        })
    }

    private fun generateFromSuggestion(keywordMap: Map<Locale, Set<String>>) {
        val regex = Regex("[A-Za-zÄÜÖäüö]+")//TODO
        keywordMap.forEach { (locale, keywords) ->
            keywords.forEach {
                addKeywordChip(regex.find(it)!!.value, locale)
            }
        }
    }

    private fun buildLanguageDialog() {

        viewModel.languageChoices.observe(viewLifecycleOwner, Observer { choices ->

            dialog = MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Choose the Language of your new Deck")
                    .setItems(choices.map { it.displayLanguage }.toTypedArray()) { _, i ->
                        viewModel.switchDeckLang(i)
                        keywords.clear()
                    }
                    .create()
        })
    }

    private fun onAddText(v: EditText) {
        val text = v.text.toString().trim()
        addKeywordChip(text, suggestionLang)
        v.text.clear()
    }

    private fun addKeywordChip(t: String, lang: Locale) {

        val regex = Regex("[A-Za-zÄÜÖäüö]+")
        val text = regex.find(t)!!.value

        val chip = LayoutInflater.from(requireContext()).inflate(R.layout.deck_creation_input_chip, keywordsChipGroup, false) as Chip
        chip.text = text
        chip.isCheckable = false

        val src = resources.flagDrawableOf(lang).toBitmap()
        val roundedSrc = RoundedBitmapDrawableFactory.create(resources, src)
        roundedSrc.cornerRadius = max(src.width, src.height) / 2f
        roundedSrc.isCircular = true
        chip.chipIcon = roundedSrc
        chip.setOnCloseIconClickListener(this::onCloseChip)
        keywordsChipGroup.addView(chip)

        try {
            viewModel.fetchSuggestions(chip.id, text, lang)
        } catch (e: Exception) {
            Log.e(TAG, "DeckCreationFragment: ", e)
        }

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

        deckCreationBackdropButton.animate().rotation(270f).alpha(0.5f).setDuration(100).withEndAction {
            deckCreationBackdropButton.setImageResource(R.drawable.ic_baseline_build_24)
            deckCreationBackdropButton.animate().rotation(360f).alpha(1f).setDuration(100).start()
        }.start()
        deckCreationCreateDeckButton.shrink()
        deckCreationCreateDeckButton.hide()
        finishDeckFab.show()
        animateOut(deckCreationHeaderCollapsed)
        animateIn(deckCreationHeaderExpanded)
    }

    private fun collapse(sheetBehavior: BottomSheetBehavior<LinearLayout>) {
        Handler().postDelayed({ sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED }, 100)
        deckCreationBackdropButton.rotation = 0f
        imm.hideSoftInputFromWindow(requireView().windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        deckCreationBackdropButton.animate().rotation(90f).alpha(0.5f).setDuration(100).withEndAction {
            deckCreationBackdropButton.setImageResource(R.drawable.ic_baseline_list_24)
            deckCreationBackdropButton.animate().rotation(180f).alpha(1f).setDuration(100).withEndAction {
                deckCreationCreateDeckButton.show()
                deckCreationCreateDeckButton.extend()
            }.start()
        }.start()

        finishDeckFab.hide()
        animateIn(deckCreationHeaderCollapsed)
        animateOut(deckCreationHeaderExpanded)
    }

}
