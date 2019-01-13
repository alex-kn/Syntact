package com.alexkn.syntact.presentation.hangman.board;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Phrase;

public class PhraseViewHolder extends ListItemViewHolder<Phrase> {

    private TextView clueTextView;

    private TextView solutionTextView;

    PhraseViewHolder(View v) {

        super(v);
        v.setFocusable(false);
        clueTextView = v.findViewById(R.id.clueTextView);
        solutionTextView = v.findViewById(R.id.solutionTextView);
    }

    @Override
    public void bindTo(Phrase phrase) {

        clueTextView.setText(phrase.getClue());
        solutionTextView.setText(phrase.getAttempt());
    }
}
