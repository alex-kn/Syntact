package com.alexkn.syntact.presentation.deckdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.alexkn.syntact.R
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        holder.deleteButton.setOnClickListener {
            dialogBuilder
                    .setTitle("Delete Item")
                    .setMessage("The Item will stop appearing in this Flashcard Stack")
                    .setPositiveButton("Delete") { dialog, _ ->
                        deleteItemListener.accept(solvableTranslationCto)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }.create().show()
        }

        holder.bindTo(solvableTranslationCto)
    }


    class SolvableItemViewHolder(v: View) : ListItemViewHolder<SolvableTranslationCto>(v) {

        private val solutionTextView: TextView = itemView.findViewById(R.id.solutionTextView)
        private val clueTextView: TextView = itemView.findViewById(R.id.clueTextView)

        private val itemInfo: TextView = itemView.findViewById(R.id.itemInfo)

        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteImage)

        override fun bindTo(entity: SolvableTranslationCto) {


            solutionTextView.text = entity.solvableItem.text
            clueTextView.text = entity.clue?.text
//            val solvableItem = entity.solvableItem

//            val duration = Duration.between(Instant.now(), solvableItem.nextDueDate)
//            val durationString = when {
//                duration.toDays() > 0 -> DurationFormatUtils.formatDuration(duration.toMillis(), "d'd' HH'h'", false)
//                duration.isNegative -> "now"
//                else -> DurationFormatUtils.formatDuration(duration.toMillis(), "H'h'", false)
//            }

//            val text = String.format("Due %s", durationString)

            itemInfo.text = String.format("Solved %d times", entity.solvableItem.timesSolved)
        }

    }

}