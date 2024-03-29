package dev.alexknittel.syntact.presentation.common

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import dev.alexknittel.syntact.core.model.Identifiable

class DiffCallback<T : Identifiable<*>> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}
