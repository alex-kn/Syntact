package com.alexkn.syntact.presentation.hangman;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.presentation.common.ListItemViewHolder;

class LetterViewHolder extends ListItemViewHolder<Letter> {

    private TextView textView;

    private String text = "";

    LetterViewHolder(View v) {

        super(v);
        textView = v.findViewById(R.id.characterTextView);
        textView.setText(text);

    }

    public void bindTo(Letter letter) {

        if (textView != null) {
            textView.setText(letter.getCharacter().toString());
        }
        text = letter.getCharacter().toString();
    }
}
