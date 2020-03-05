package com.alexkn.syntact.presentation.decklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.alexkn.syntact.R
import com.alexkn.syntact.data.model.DeckDetail
import com.alexkn.syntact.databinding.DeckListItemBinding
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.google.android.material.button.MaterialButton
import kotlin.math.ceil

class DeckListItemAdapter : ListItemAdapter<DeckDetail, DeckListItemAdapter.DeckListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckListItemViewHolder {

        val dataBinding = DataBindingUtil
                .inflate<DeckListItemBinding>(LayoutInflater.from(parent.context), R.layout.deck_list_item, parent, false)

        val drawable = ResourcesCompat.getDrawable(parent.resources, R.drawable.fr, null)
        dataBinding.flag = drawable

        return DeckListItemViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: DeckListItemViewHolder, position: Int) {

        val bucket = list[position]
        holder.bindTo(bucket)
    }

    class DeckListItemViewHolder(private val binding: DeckListItemBinding) : ListItemViewHolder<DeckDetail>(binding.root) {

        private lateinit var deck: DeckDetail

        override fun bindTo(entity: DeckDetail) {

            this.deck = entity

            binding.bucket = entity

            binding.imageView2.clipToOutline = true

            val resId = itemView.context.resources.getIdentifier(entity.language.language, "drawable", itemView.context.packageName)
            val drawable = ResourcesCompat.getDrawable(itemView.resources, resId, null)
            binding.flag = drawable

            binding.progress = ceil(100 - deck.dueCount.toDouble() / deck.itemCount * 100).toInt()

            val startButton = itemView.findViewById<MaterialButton>(R.id.startButton)
            startButton.setOnClickListener(this::startFlashcards)

            val optionsButton = itemView.findViewById<MaterialButton>(R.id.optionsButton)
            optionsButton.setOnClickListener(this::showBucketDetails)


        }

        private fun startFlashcards(view: View) {

            val action = DeckListFragmentDirections
                    .actionDeckListFragmentToDeckBoardFragment(deck.id)
            Navigation.findNavController(view).navigate(action)
        }

        private fun showBucketDetails(view: View) {
            val action = DeckListFragmentDirections.actionDeckListFragmentToDeckDetailsFragment(deck.id)
            Navigation.findNavController(view).navigate(action)
        }
    }
}