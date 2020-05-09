package dev.alexknittel.syntact.presentation.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.alexknittel.syntact.core.model.Identifiable

abstract class ListItemViewHolder<T : Identifiable<*>>(v: View) : RecyclerView.ViewHolder(v) {

    abstract fun bindTo(entity: T)
}
