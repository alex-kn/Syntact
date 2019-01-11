package com.alexkn.syntact.presentation.hangman.board;

import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;

import androidx.annotation.NonNull;

public class LetterListAdapter extends ListItemAdapter<Letter, LetterViewHolder> {

    @NonNull
    @Override
    public LetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.letter_card, parent, false);
        return new LetterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterViewHolder holder, int position) {

        Letter letter = getList().get(position);
        holder.bindTo(letter);

        holder.itemView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.performClick();
                v.startDragAndDrop(null, new LetterDragShadow(v), letter, View.DRAG_FLAG_OPAQUE);
            }

            return false;
        });
    }

    private class LetterDragShadow extends View.DragShadowBuilder {

        LetterDragShadow(View view) {

            super(view);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {

            View v = getView();
            int height = v.getHeight();
            int width = v.getWidth();
            shadowSize.set(width, height);
            shadowTouchPoint.set((width / 2), height);
        }
    }
}
