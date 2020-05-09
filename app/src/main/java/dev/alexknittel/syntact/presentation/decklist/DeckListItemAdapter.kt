package dev.alexknittel.syntact.presentation.decklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import dev.alexknittel.syntact.R
import dev.alexknittel.syntact.core.model.DeckListItem
import dev.alexknittel.syntact.databinding.DeckListItemBinding
import dev.alexknittel.syntact.presentation.common.ListItemAdapter
import dev.alexknittel.syntact.presentation.common.ListItemViewHolder

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

            binding.solvedIndicator.visibility = if (item.newItemsToday + item.reviewsToday == 0) View.VISIBLE else View.GONE

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
