package dev.alexknittel.syntact.presentation.decklist.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.alexknittel.syntact.R
import dev.alexknittel.syntact.databinding.DeckListLanguageDialogItemBinding
import dev.alexknittel.syntact.presentation.common.flagDrawableOf
import java.util.*

class DeckListLanguageDialogItemAdaper(
        private val items: List<Pair<Locale, Locale>>,
        private val onChooseLanguage: (Locale) -> Unit
) : RecyclerView.Adapter<DeckListLanguageDialogItemAdaper.DeckListLanguageDialogItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckListLanguageDialogItemViewHolder {

        val dataBinding = DataBindingUtil
                .inflate<DeckListLanguageDialogItemBinding>(LayoutInflater.from(parent.context), R.layout.deck_list_language_dialog_item, parent, false)
        return DeckListLanguageDialogItemViewHolder(dataBinding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DeckListLanguageDialogItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.flagLeft = holder.itemView.resources.flagDrawableOf(item.first)
        holder.binding.langLeft = item.first.displayLanguage
        holder.binding.langRight = item.second.displayLanguage
        holder.binding.flagRight = holder.itemView.resources.flagDrawableOf(item.second)
        holder.itemView.setOnClickListener { onChooseLanguage.invoke(item.second) }
        holder.binding.deckListLanguageDialogItemImageLeft.clipToOutline = true
        holder.binding.deckListLanguageDialogItemImageRight.clipToOutline = true
    }

    class DeckListLanguageDialogItemViewHolder(val binding: DeckListLanguageDialogItemBinding) : RecyclerView.ViewHolder(binding.root)

}
