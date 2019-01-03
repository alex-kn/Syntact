package com.alexkn.syntact.hangwords.ui;

import com.alexkn.syntact.hangwords.util.DiffCallback;
import com.alexkn.syntact.hangwords.util.Identifiable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ListItemAdapter<T extends Identifiable, S extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<S> {

    private final AsyncListDiffer<T> differ = new AsyncListDiffer<>(this, onDiff());

    DiffUtil.ItemCallback<T> onDiff(){
        return new DiffCallback<>();
    }

    public void submitList(List<T> data) {
        differ.submitList(data);
    }

    public void addItem(T item) {
        ArrayList<T> data = new ArrayList<>(differ.getCurrentList());
        data.add(item);
        differ.submitList(data);
    }

    public void removeItem(T item) {
        ArrayList<T> data = new ArrayList<>(differ.getCurrentList());
        data.remove(item);
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
