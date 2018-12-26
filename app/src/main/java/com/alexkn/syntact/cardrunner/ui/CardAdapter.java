package com.alexkn.syntact.cardrunner.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexkn.syntact.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    public List<Phrase> phrases;

    static class CardViewHolder extends RecyclerView.ViewHolder {

        TextView clueTextView;
        TextView solutionTextView;

        CardViewHolder(View v) {
            super(v);
            clueTextView = v.findViewById(R.id.clueTextView);
            solutionTextView = v.findViewById(R.id.solutionTextView);
        }
    }

    public CardAdapter(List<Phrase> phrases) {
        this.phrases = phrases;
    }

    @NonNull
    @Override
    public CardAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .card_runner_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.CardViewHolder holder, int position) {
        holder.clueTextView.setText(phrases.get(position).getClue());
        holder.solutionTextView.setText(phrases.get(position).getSolution());
    }

    @Override
    public int getItemCount() {
        return phrases.size();
    }
}
