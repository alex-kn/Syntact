package com.alexkn.syntact.hangwords.ui;

import android.view.View;

import com.alexkn.syntact.hangwords.util.Identifiable;

import androidx.recyclerview.widget.RecyclerView;

public abstract class ListItemViewHolder<T extends Identifiable> extends RecyclerView.ViewHolder {
    public ListItemViewHolder(View v) {
        super(v);
    }

    abstract void bindTo(T entity);
}
