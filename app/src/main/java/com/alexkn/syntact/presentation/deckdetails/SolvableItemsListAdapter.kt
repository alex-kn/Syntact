package com.alexkn.syntact.presentation.deckdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alexkn.syntact.R
import com.alexkn.syntact.data.model.SolvableTranslationCto
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.apache.commons.lang3.time.DurationFormatUtils
import java.time.Duration
import java.time.Instant
import java.util.function.Consumer


class SolvableItemsListAdapter : ListItemAdapter<SolvableTranslationCto, SolvableItemsListAdapter.SolvableItemViewHolder>() {

    lateinit var deleteItemListener: Consumer<SolvableTranslationCto>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolvableItemViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.deck_details_item, parent, false)
        return SolvableItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SolvableItemViewHolder, position: Int) {

        val solvableTranslationCto = list[position]

        val dialogBuilder = MaterialAlertDialogBuilder(holder.itemView.context)

//        holder.deleteButton.setOnClickListener {
//            dialogBuilder
//                    .setTitle("Delete Item")
//                    .setMessage("The Item will be delete from this Deck")
//                    .setPositiveButton("Delete") { dialog, _ ->
//                        deleteItemListener.accept(solvableTranslationCto)
//                        dialog.dismiss()
//                    }
//                    .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }.create().show()
//        }

        holder.bindTo(solvableTranslationCto)
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
            val durationString = when {
                duration.toDays() > 1 -> DurationFormatUtils.formatDuration(duration.toMillis(), "d' Days'", false)
                duration.toDays() > 0 -> DurationFormatUtils.formatDuration(duration.toMillis(), "d' Day'", false)
                duration.toHours() > 1 -> DurationFormatUtils.formatDuration(duration.toMillis(), "H' Hours'", false)
                duration.toHours() > 0 -> DurationFormatUtils.formatDuration(duration.toMillis(), "H' Hour'", false)
                duration.isNegative || duration.isZero -> "now"
                else -> DurationFormatUtils.formatDuration(duration.toMillis(), "m' Minutes'", false)
            }
            dueOutput.text = durationString
            streakOutput.text = entity.solvableItem.consecutiveCorrectAnswers.toString()
            easeOutput.text = "%.2f".format(entity.solvableItem.easiness)
        }

    }

}