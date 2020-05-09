package dev.alexknittel.syntact.presentation.deckcreation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.alexknittel.syntact.R
import dev.alexknittel.syntact.databinding.DeckCreationItemBinding
import dev.alexknittel.syntact.presentation.common.ListItemAdapter
import dev.alexknittel.syntact.presentation.common.ListItemViewHolder
import dev.alexknittel.syntact.presentation.deckcreation.detail.DeckCreationDetailDialog
import dev.alexknittel.syntact.service.Suggestion
import java.util.*

class DeckCreationItemAdapter : ListItemAdapter<Suggestion, DeckCreationItemAdapter.DeckCreationItemViewHolder>() {

    lateinit var onDeleteListener: (Suggestion) -> Unit
    lateinit var onSaveListener: (Map<Locale, Set<String>>) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckCreationItemViewHolder {

        val binding = DataBindingUtil.inflate<DeckCreationItemBinding>(
                LayoutInflater.from(parent.context), R.layout.deck_creation_item, parent, false
        )
        return DeckCreationItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeckCreationItemViewHolder, position: Int) {

        val phraseSuggestionResponse = list[position]
        holder.bindTo(phraseSuggestionResponse)

        holder.itemView.setOnClickListener {

            with(DeckCreationDetailDialog()) {
                onDelete = onDeleteListener
                onSave = onSaveListener
                bindTo(phraseSuggestionResponse)
                show((holder.itemView.context as AppCompatActivity).supportFragmentManager, DeckCreationDetailDialog::class.simpleName)
            }
        }
    }

    class DeckCreationItemViewHolder(val binding: DeckCreationItemBinding) : ListItemViewHolder<Suggestion>(binding.root) {

        override fun bindTo(entity: Suggestion) {
            binding.destText = entity.dest
            binding.srcText = entity.src
        }
    }

}
