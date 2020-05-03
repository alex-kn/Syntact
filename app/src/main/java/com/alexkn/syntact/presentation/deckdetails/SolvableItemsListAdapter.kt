package com.alexkn.syntact.presentation.deckdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alexkn.syntact.R
import com.alexkn.syntact.core.model.SolvableTranslationCto
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.alexkn.syntact.presentation.common.toUiString
import com.alexkn.syntact.presentation.deckdetails.detail.DeckDetailsDetailDialog
import java.time.Duration
import java.time.Instant


class SolvableItemsListAdapter : ListItemAdapter<SolvableTranslationCto, SolvableItemsListAdapter.SolvableItemViewHolder>() {

    lateinit var onDeleteListener: (SolvableTranslationCto) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolvableItemViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.deck_details_item, parent, false)
        return SolvableItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SolvableItemViewHolder, position: Int) {

        val solvableTranslationCto = list[position]
        holder.bindTo(solvableTranslationCto)

        holder.itemView.setOnClickListener {
            with(DeckDetailsDetailDialog()) {
                onDelete = onDeleteListener
                bindTo(solvableTranslationCto)
                show((holder.itemView.context as AppCompatActivity).supportFragmentManager, DeckDetailsDetailDialog::class.simpleName)
            }
        }
    }

    class SolvableItemViewHolder(v: View) : ListItemViewHolder<SolvableTranslationCto>(v) {

        private val solutionTextView: TextView = itemView.findViewById(R.id.solutionTextView)
        private val clueTextView: TextView = itemView.findViewById(R.id.clueTextView)

        private val dueOutput: TextView = itemView.findViewById(R.id.deckDetailsDueOutput)
        private val streakOutput: TextView = itemView.findViewById(R.id.deckDetailsStreakOutput)
        private val easeOutput: TextView = itemView.findViewById(R.id.deckDetailsEaseOutput)

        override fun bindTo(entity: SolvableTranslationCto) {

            solutionTextView.text = entity.solvableItem.text
            clueTextView.text = entity.clue.text

            val duration = Duration.between(Instant.now(), entity.solvableItem.nextDueDate ?: Instant.now())
            dueOutput.text = duration.toUiString()
            streakOutput.text = entity.solvableItem.consecutiveCorrectAnswers.toString()
            easeOutput.text = "%.2f".format(entity.solvableItem.easiness)
        }

    }

}