package com.alexkn.syntact.presentation.hangman.board;

import android.view.View;

import com.alexkn.syntact.domain.common.Identifiable;

import androidx.recyclerview.widget.RecyclerView;

abstract class ListItemViewHolder<T extends Identifiable> extends RecyclerView.ViewHolder {
    ListItemViewHolder(View v) {
        super(v);
    }

    abstract void bindTo(T entity);
}
