package com.alexkn.syntact.hangwords.ui;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;

class PhraseViewHolder extends EntityViewHolder<Phrase> {

    private TextView clueTextView;
    private TextView solutionTextView;

    PhraseViewHolder(View v) {
        super(v);
        clueTextView = v.findViewById(R.id.clueTextView);
        solutionTextView = v.findViewById(R.id.solutionTextView);
    }

    @Override
    void bindTo(Phrase phrase) {
        clueTextView.setText(phrase.getClue());
        solutionTextView.setText(phrase.getSolution());
    }


}
