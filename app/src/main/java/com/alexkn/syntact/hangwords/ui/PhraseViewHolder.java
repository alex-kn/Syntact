package com.alexkn.syntact.hangwords.ui;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.hangwords.logic.SolvablePhrase;

class PhraseViewHolder extends ListItemViewHolder<SolvablePhrase> {

    private TextView clueTextView;
    private TextView solutionTextView;

    PhraseViewHolder(View v) {
        super(v);
        v.setFocusable(false);
        clueTextView = v.findViewById(R.id.clueTextView);
        solutionTextView = v.findViewById(R.id.solutionTextView);
    }

    @Override
    public void bindTo(SolvablePhrase phrase) {
        clueTextView.setText(phrase.getClue());
        solutionTextView.setText(phrase.getCurrentText());
    }
}
