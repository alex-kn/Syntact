package com.alexkn.syntact.presentation.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.presentation.hangman.ListItemAdapter;

import androidx.annotation.NonNull;

public class LanguageAdapter extends ListItemAdapter<LanguagePair, LanguageViewHolder> {

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.language_card, parent, false);

        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {

        holder.bindTo(getList().get(position));
    }
}
