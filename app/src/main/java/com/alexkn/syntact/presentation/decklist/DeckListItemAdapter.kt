package com.alexkn.syntact.presentation.decklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.alexkn.syntact.R
import com.alexkn.syntact.data.model.DeckListItem
import com.alexkn.syntact.databinding.DeckListItemBinding
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.google.android.material.button.MaterialButton

class DeckListItemAdapter : ListItemAdapter<DeckListItem, DeckListItemAdapter.DeckListItemViewHolder>() {

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

    class DeckListItemViewHolder(private val binding: DeckListItemBinding) : ListItemViewHolder<DeckListItem>(binding.root) {

        private lateinit var item: DeckListItem

        override fun bindTo(entity: DeckListItem) {

            this.item = entity
            binding.item = entity

            binding.imageView2.clipToOutline = true

            val resId = itemView.context.resources.getIdentifier(entity.deck.language.language, "drawable", itemView.context.packageName)
            val drawable = ResourcesCompat.getDrawable(itemView.resources, resId, null)
            binding.flag = drawable

            binding.solvedIndicator.visibility = if (item.newItemsToday == 0 && item.reviewsToday == 0) View.VISIBLE else View.GONE

//            val solvedToday = item.solvedToday.toDouble()
//            val dueToday = item.newItemsToday + item.reviewsToday
//            binding.progress = ceil(solvedToday / (dueToday + solvedToday) * 100).toInt()

            val startButton = itemView.findViewById<MaterialButton>(R.id.startButton)
            startButton.setOnClickListener(this::startFlashcards)

            val optionsButton = itemView.findViewById<MaterialButton>(R.id.optionsButton)
            optionsButton.setOnClickListener(this::showBucketDetails)


        }

        private fun startFlashcards(view: View) {

            val action = DeckListFragmentDirections
                    .actionDeckListFragmentToDeckBoardFragment(item.deck.id!!)
            Navigation.findNavController(view).navigate(action)
        }

        private fun showBucketDetails(view: View) {
            val action = DeckListFragmentDirections.actionDeckListFragmentToDeckDetailsFragment(item.deck.id!!)
            Navigation.findNavController(view).navigate(action)
        }
    }
}
