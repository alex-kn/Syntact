package com.alexkn.syntact.presentation.deckselection

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.alexkn.syntact.R
import com.alexkn.syntact.data.model.Template
import com.alexkn.syntact.databinding.DeckSelectionItemBinding
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.google.android.material.button.MaterialButton
import java.util.function.Consumer


class DeckSelectionItemAdapter : ListItemAdapter<Template, DeckSelectionItemAdapter.DeckSelectionItemViewHolder>() {

    lateinit var createListener: Consumer<Template>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckSelectionItemViewHolder {

        val binding = DataBindingUtil.inflate<DeckSelectionItemBinding>(LayoutInflater.from(parent.context), R.layout.deck_selection_item, parent, false)
        return DeckSelectionItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeckSelectionItemViewHolder, position: Int) {

        val template = list[position]
        holder.bindTo(template)
        holder.createButton.setOnClickListener { createListener.accept(template) }
    }

    class DeckSelectionItemViewHolder(var databinding: DeckSelectionItemBinding) : ListItemViewHolder<Template>(databinding.root) {

        var createButton: MaterialButton = itemView.findViewById(R.id.chooseButton)

        var flagImage: ImageView = itemView.findViewById(R.id.flagImage)

        override fun bindTo(entity: Template) {
            flagImage.clipToOutline = true
            databinding.template = entity
            val resId = itemView.context.resources.getIdentifier(entity.language.language, "drawable", itemView.context.packageName)
            flagImage.setImageResource(resId)
        }
    }
}

