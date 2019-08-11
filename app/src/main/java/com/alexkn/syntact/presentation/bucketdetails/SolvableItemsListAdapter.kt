package com.alexkn.syntact.presentation.bucketdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.alexkn.syntact.R
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.apache.commons.lang3.time.DurationFormatUtils
import java.time.Duration
import java.time.Instant
import java.util.function.Consumer


class SolvableItemsListAdapter : ListItemAdapter<SolvableTranslationCto, SolvableItemsListAdapter.SolvableItemViewHolder>() {

    lateinit var deleteItemListener: Consumer<SolvableTranslationCto>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolvableItemViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bucket_details_item, parent, false)
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

        private val clueTextView: TextView = itemView.findViewById(R.id.clueTextView)

        private val itemInfo: TextView = itemView.findViewById(R.id.itemInfo)

        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteImage)

        private val card: MaterialCardView = itemView as MaterialCardView

        override fun bindTo(entity: SolvableTranslationCto) {


            clueTextView.text = entity.clue.text
            val solvableItem = entity.solvableItem

            val duration = Duration.between(Instant.now(), solvableItem.nextDueDate)
            val durationString = when {
                duration.toDays() > 0 -> DurationFormatUtils.formatDuration(duration.toMillis(), "d'd' HH'h'", false)
                duration.isNegative -> "now"
                else -> DurationFormatUtils.formatDuration(duration.toMillis(), "H'h'", false)
            }

            card.strokeWidth = 1

            if (entity.solvableItem.consecutiveCorrectAnswers > 0) {
                card.strokeColor = ContextCompat.getColor(card.context, R.color.color_success)
            } else {
                card.strokeColor = ContextCompat.getColor(card.context, R.color.color_error)
            }

            val text = String.format("Due %s", durationString)
            itemInfo.text = text
        }

    }

}