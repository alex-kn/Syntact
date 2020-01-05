package com.alexkn.syntact.presentation.createtemplate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alexkn.syntact.R
import com.alexkn.syntact.databinding.CreateTemplateItemBinding
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.alexkn.syntact.service.PhraseSuggestionResponse

class SuggestionListAdapter : ListItemAdapter<PhraseSuggestionResponse, SuggestionListAdapter.SuggestionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {

        val binding = DataBindingUtil.inflate<CreateTemplateItemBinding>(
                LayoutInflater.from(parent.context), R.layout.create_template_item, parent, false
        )
        return SuggestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val phraseSuggestionResponse = list[position]
        holder.bindTo(phraseSuggestionResponse)
    }

    class SuggestionViewHolder(val binding: CreateTemplateItemBinding) : ListItemViewHolder<PhraseSuggestionResponse>(binding.root) {

        override fun bindTo(entity: PhraseSuggestionResponse) {
            binding.destText = entity.dest
            binding.srcText = entity.src
        }
    }

}
