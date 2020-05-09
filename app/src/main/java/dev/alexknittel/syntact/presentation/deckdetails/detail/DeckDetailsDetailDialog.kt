package dev.alexknittel.syntact.presentation.deckdetails.detail

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import dev.alexknittel.syntact.R
import dev.alexknittel.syntact.core.model.SolvableTranslationCto
import kotlinx.android.synthetic.main.deck_details_detail_dialog.*

class DeckDetailsDetailDialog : DialogFragment() {

    lateinit var onDelete: (SolvableTranslationCto) -> Unit

    private lateinit var solvableTranslationCto: SolvableTranslationCto

    private var deleteConfirm = false

    private var handler: Handler = Handler()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 32))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.deck_details_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        deckDetailsDetailDialogTopTextView.text = solvableTranslationCto.solvableItem.text
        deckDetailsDetailDialogBottomTextView.text = solvableTranslationCto.clue.text

        deckDetailsDetailDeleteButton.setOnClickListener {
            if (deleteConfirm) {
                onDelete.invoke(solvableTranslationCto)
                dialog?.dismiss()
            } else {
                (it as MaterialButton).text = resources.getString(R.string.deck_details_detail_dialog_delete_button_confirm)
                deleteConfirm = true
                handler.postDelayed({
                    it.text = resources.getString(R.string.deck_details_detail_dialog_delete_button)
                    deleteConfirm = false
                }, 2000)
            }
        }

        deckDetailsDetailCancelButton.setOnClickListener { dialog?.dismiss() }
    }

    override fun onDismiss(dialog: DialogInterface) {
        handler.removeCallbacksAndMessages(null)
        super.onDismiss(dialog)
    }

    fun bindTo(solvableTranslationCto: SolvableTranslationCto) {
        this.solvableTranslationCto = solvableTranslationCto
    }

    private fun setupSentence(text: String, layout: ViewGroup) {
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(2, 1, 2, 1)
        text.split(" ").forEach {
            val materialTextView = MaterialTextView(requireContext())
            materialTextView.layoutParams = layoutParams
            materialTextView.setTextAppearance(R.style.TextAppearance_MyTheme_Headline5)
            materialTextView.text = it
            layout.addView(materialTextView)

        }
    }
}