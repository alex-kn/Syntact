package com.alexkn.syntact.presentation.deckcreation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alexkn.syntact.R
import com.alexkn.syntact.databinding.DeckCreationItemBinding
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.alexkn.syntact.service.Suggestion

class DeckCreationItemAdapter : ListItemAdapter<Suggestion, DeckCreationItemAdapter.DeckCreationItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckCreationItemViewHolder {

        val binding = DataBindingUtil.inflate<DeckCreationItemBinding>(
                LayoutInflater.from(parent.context), R.layout.deck_creation_item, parent, false
        )
        return DeckCreationItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeckCreationItemViewHolder, position: Int) {
        val phraseSuggestionResponse = list[position]
        holder.bindTo(phraseSuggestionResponse)
    }

    class DeckCreationItemViewHolder(val binding: DeckCreationItemBinding) : ListItemViewHolder<Suggestion>(binding.root) {

        override fun bindTo(entity: Suggestion) {
            binding.destText = entity.dest
            binding.srcText = entity.src
        }
    }

}
