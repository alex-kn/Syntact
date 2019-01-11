package com.alexkn.syntact.presentation.hangman.board;

import com.alexkn.syntact.domain.common.Identifiable;

import java.util.List;

import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ListItemAdapter<T extends Identifiable, S extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<S> {

    private final AsyncListDiffer<T> differ = new AsyncListDiffer<>(this, onDiff());

    private DiffUtil.ItemCallback<T> onDiff(){
        return new DiffCallback<>();
    }

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
