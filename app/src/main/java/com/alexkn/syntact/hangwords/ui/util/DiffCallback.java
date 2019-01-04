package com.alexkn.syntact.hangwords.ui.util;

import com.alexkn.syntact.hangwords.util.Identifiable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DiffCallback<T extends Identifiable> extends DiffUtil
        .ItemCallback<T> {

    @Override
    public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull T oldItem, @NonNull
            T newItem) {
        return oldItem.equals(newItem);
    }
}
