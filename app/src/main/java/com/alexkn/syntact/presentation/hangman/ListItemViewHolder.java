package com.alexkn.syntact.presentation.hangman;

import android.view.View;

import com.alexkn.syntact.domain.common.Identifiable;

import androidx.recyclerview.widget.RecyclerView;

public abstract class ListItemViewHolder<T extends Identifiable> extends RecyclerView.ViewHolder {
    public ListItemViewHolder(View v) {
        super(v);
    }

    public abstract void bindTo(T entity);
}
