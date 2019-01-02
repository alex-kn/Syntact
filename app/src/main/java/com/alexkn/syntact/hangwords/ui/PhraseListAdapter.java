package com.alexkn.syntact.hangwords.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.alexkn.syntact.hangwords.logic.SolvablePhrase;

import androidx.annotation.NonNull;

public class PhraseListAdapter extends ListItemAdapter<SolvablePhrase, PhraseViewHolder> {
    @NonNull
    @Override
    public PhraseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .phrase_card, parent, false);
        return new PhraseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhraseViewHolder holder, int position) {
        holder.bindTo(getList().get(position));
    }
}
