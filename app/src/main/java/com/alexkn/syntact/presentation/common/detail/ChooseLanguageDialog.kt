package com.alexkn.syntact.presentation.common.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import com.alexkn.syntact.databinding.CommonChooseLanguageDialogItemBinding
import com.alexkn.syntact.presentation.common.flagDrawableOf
import kotlinx.android.synthetic.main.common_choose_language_dialog.*
import java.util.*

class ChooseLanguageDialog(private val header: String) : DialogFragment() {

    private lateinit var list: List<Locale>
    private lateinit var onChooseLanguage: (Locale) -> Unit

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 32))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.common_choose_language_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        chooseLanguageTitleTextView.text = header
        chooseLanguageList.layoutManager = LinearLayoutManager(requireContext())
        chooseLanguageList.adapter = ChooseLanguageDialogItemAdapter(list, onChooseLanguage)
    }

    fun bindTo(list: List<Locale>, onChooseLanguage: (Locale) -> Unit) {
        this.list = list
        this.onChooseLanguage = onChooseLanguage
    }

}

class ChooseLanguageDialogItemAdapter(
        private val items: List<Locale>,
        private val onChooseLanguage: (Locale) -> Unit
) : RecyclerView.Adapter<ChooseLanguageDialogItemAdapter.ChooseLanguageDialogItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseLanguageDialogItemViewHolder {
        val dataBinding = DataBindingUtil
                .inflate<CommonChooseLanguageDialogItemBinding>(LayoutInflater.from(parent.context), R.layout.common_choose_language_dialog_item, parent, false)
        return ChooseLanguageDialogItemViewHolder(dataBinding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ChooseLanguageDialogItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.flagLeft = holder.itemView.resources.flagDrawableOf(item)
        holder.binding.langLeft = item.displayLanguage
        holder.itemView.setOnClickListener { onChooseLanguage.invoke(item) }
        holder.binding.chooseLanguageDialogItemImageLeft.clipToOutline = true
    }

    class ChooseLanguageDialogItemViewHolder(val binding: CommonChooseLanguageDialogItemBinding) : RecyclerView.ViewHolder(binding.root)

}
