package com.alexkn.syntact.hangwords.ui;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.google.android.material.card.MaterialCardView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;

public class LetterListAdapter extends EntityListAdapter<Letter, LetterViewHolder> {

    @NonNull
    @Override
    public LetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .letter_card, parent, false);
        return new LetterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterViewHolder holder, int position) {
        Letter letter = getList().get(position);
        holder.bindTo(letter);
    }
}
