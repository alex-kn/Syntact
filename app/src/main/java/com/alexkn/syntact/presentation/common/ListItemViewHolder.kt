package com.alexkn.syntact.presentation.common

import android.view.View

import com.alexkn.syntact.data.common.Identifiable

import androidx.recyclerview.widget.RecyclerView

abstract class ListItemViewHolder<T : Identifiable<*>>(v: View) : RecyclerView.ViewHolder(v) {

    abstract fun bindTo(entity: T)
}
