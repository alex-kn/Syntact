package com.alexkn.syntact.hangwords.ui;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;

class LetterViewHolder extends EntityViewHolder<Letter> {


    private TextView textView;

    LetterViewHolder(View v) {
        super(v);
        textView = v.findViewById(R.id.characterTextView);
    }

    void bindTo(Letter letter) {

        textView.setText(letter.getCharacter().toString());
    }
}
