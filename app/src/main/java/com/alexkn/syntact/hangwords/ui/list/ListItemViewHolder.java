package com.alexkn.syntact.hangwords.ui.list;

import android.view.View;

import com.alexkn.syntact.hangwords.util.Identifiable;

import androidx.recyclerview.widget.RecyclerView;

abstract class ListItemViewHolder<T extends Identifiable> extends RecyclerView.ViewHolder {
    ListItemViewHolder(View v) {
        super(v);
    }

    abstract void bindTo(T entity);
}
