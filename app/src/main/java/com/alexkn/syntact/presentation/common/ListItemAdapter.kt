package com.alexkn.syntact.presentation.common

import com.alexkn.syntact.data.common.Identifiable

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class ListItemAdapter<T : Identifiable<*>, S : RecyclerView.ViewHolder> : RecyclerView.Adapter<S>() {

    private val differ = AsyncListDiffer(this, onDiff())

    val list: List<T>
        get() = differ.currentList

    private fun onDiff(): DiffUtil.ItemCallback<T> = DiffCallback()

    fun submitList(data: List<T>) = differ.submitList(data)

    override fun getItemCount(): Int = differ.currentList.size
}
