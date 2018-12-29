package com.alexkn.syntact.hangwords.ui;

import android.view.View;

import com.alexkn.syntact.hangwords.util.Entity;

import androidx.recyclerview.widget.RecyclerView;

public abstract class EntityViewHolder<T extends Entity> extends RecyclerView.ViewHolder {
    public EntityViewHolder(View v) {
        super(v);
    }

    abstract void bindTo(T entity);
}
