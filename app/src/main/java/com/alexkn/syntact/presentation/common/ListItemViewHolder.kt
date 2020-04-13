package com.alexkn.syntact.presentation.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.core.model.Identifiable

abstract class ListItemViewHolder<T : Identifiable<*>>(v: View) : RecyclerView.ViewHolder(v) {

    abstract fun bindTo(entity: T)
}
