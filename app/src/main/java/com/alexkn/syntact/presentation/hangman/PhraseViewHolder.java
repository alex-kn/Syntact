package com.alexkn.syntact.presentation.hangman;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Phrase;

import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

public class PhraseViewHolder extends ListItemViewHolder<Phrase> {

    private TextView clueTextView;

    private TextView solutionTextView;


    private String clue;
    private String solution;

    private View.OnDragListener dragListener;

    PhraseViewHolder(FrameLayout v) {

        super(v);
        v.setFocusable(false);


        AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(v.getContext());
        asyncLayoutInflater.inflate(R.layout.phrase_card, v, (view, resid, parent) -> {

            view.setOnDragListener(dragListener);
            v.addView(view);
            clueTextView = v.findViewById(R.id.clueTextView);
            solutionTextView = v.findViewById(R.id.solutionTextView);
            clueTextView.setText(clue);
            solutionTextView.setText(solution);
        });
    }

    public void setOnDrag(View.OnDragListener dragListener) {

        this.dragListener = dragListener;
    }

    @Override
    public void bindTo(Phrase phrase) {

        if (clueTextView != null) {
            clueTextView.setText(phrase.getClue());
        }
        if (solutionTextView != null) {
            solutionTextView.setText(phrase.getAttempt());
        }
        clue = phrase.getClue();
        solution = phrase.getAttempt();
    }
}
