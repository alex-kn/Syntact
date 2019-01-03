package com.alexkn.syntact.hangwords.ui;

import android.content.ClipData;
import android.graphics.Point;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.alexkn.syntact.hangwords.logic.LetterManagement;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

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
                v.startDragAndDrop(
                        ClipData.newPlainText(letter.getId().toString(), letter.getId().toString()),
                        new LetterDragShadow(v), null, View.DRAG_FLAG_OPAQUE);

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
            shadowTouchPoint.set((width / 2), height * 2);
        }
    }

}
