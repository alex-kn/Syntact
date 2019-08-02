package com.alexkn.syntact.presentation.bucketdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.util.TimeUtils
import com.alexkn.syntact.R
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import org.apache.commons.lang3.time.DurationFormatUtils
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class SolvableItemsListAdapter : ListItemAdapter<SolvableTranslationCto, SolvableItemsListAdapter.SolvableItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolvableItemViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bucket_details_item, parent, false)
        return SolvableItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SolvableItemViewHolder, position: Int) {

        val solvableTranslationCto = list[position]
        holder.bindTo(solvableTranslationCto)
    }


    class SolvableItemViewHolder(v: View) : ListItemViewHolder<SolvableTranslationCto>(v) {

        private val clueTextView: TextView = itemView.findViewById(R.id.clueTextView)

        private val solvedCountTextView: TextView = itemView.findViewById(R.id.solvedCountTextView)

        override fun bindTo(entity: SolvableTranslationCto) {

            clueTextView.text = entity.clue.text
            val solvableItem = entity.solvableItem
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withLocale(Locale.GERMANY)
                    .withZone(ZoneId.systemDefault())


            val duration = Duration.between(Instant.now(), solvableItem.nextDueDate)
            val durationString = when {
                duration.toDays() > 0 -> DurationFormatUtils.formatDuration(duration.toMillis(), "d'd' HH'h'", false)
                else -> DurationFormatUtils.formatDuration(duration.toMillis(), "H'h'", false)
            }

            val text = String.format("Due in %s", durationString)
            solvedCountTextView.text = text
        }

    }

}