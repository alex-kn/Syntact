package com.alexkn.syntact.hangwords.ui;

import com.alexkn.syntact.hangwords.util.Entity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EntityListAdapter<T extends Entity, S extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<S> {

    private final DiffUtil.ItemCallback<T> DIFF_CALLBACK = new DiffUtil
            .ItemCallback<T>() {

        @Override
        public boolean areItemsTheSame(@NonNull Entity oldItem, @NonNull Entity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Entity oldItem, @NonNull Entity newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final AsyncListDiffer<T> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);


    public void submitList(List<T> data) {
        differ.submitList(data);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public List<T> getList() {
        return differ.getCurrentList();
    }
}
