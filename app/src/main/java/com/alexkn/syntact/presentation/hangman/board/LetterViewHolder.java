package com.alexkn.syntact.presentation.hangman.board;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;

class LetterViewHolder extends ListItemViewHolder<Letter> {

    private TextView textView;

    LetterViewHolder(View v) {
        super(v);
        textView = v.findViewById(R.id.characterTextView);
    }

    void bindTo(Letter letter) {
        textView.setText(letter.getCharacter().toString());
    }

}
