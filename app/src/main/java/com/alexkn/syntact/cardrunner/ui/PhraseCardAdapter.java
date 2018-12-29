package com.alexkn.syntact.cardrunner.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexkn.syntact.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class PhraseCardAdapter extends RecyclerView.Adapter<PhraseCardAdapter.CardViewHolder> {

    private final AsyncListDiffer<Phrase> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    static class CardViewHolder extends RecyclerView.ViewHolder {

        TextView clueTextView;
        TextView solutionTextView;

        CardViewHolder(View v) {
            super(v);
            clueTextView = v.findViewById(R.id.clueTextView);
            solutionTextView = v.findViewById(R.id.solutionTextView);
        }

        void bindTo(Phrase phrase) {
            clueTextView.setText(phrase.getClue());
            solutionTextView.setText(phrase.getSolution());
        }
    }

    public void submitList(List<Phrase> data){
        differ.submitList(data);
    }

    @NonNull
    @Override
    public PhraseCardAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .card_runner_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhraseCardAdapter.CardViewHolder holder, int position) {
        holder.bindTo(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    private static final DiffUtil.ItemCallback<Phrase> DIFF_CALLBACK = new DiffUtil.ItemCallback<Phrase>() {

        @Override
        public boolean areItemsTheSame(@NonNull Phrase oldItem, @NonNull Phrase newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Phrase oldItem, @NonNull Phrase newItem) {
            return oldItem.equals(newItem);
        }
    };
}
