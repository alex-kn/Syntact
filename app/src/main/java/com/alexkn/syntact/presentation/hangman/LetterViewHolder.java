package com.alexkn.syntact.presentation.hangman;

import android.widget.FrameLayout;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Letter;

import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

class LetterViewHolder extends ListItemViewHolder<Letter> {

    private TextView textView;

    private String text = "";

    LetterViewHolder(FrameLayout v) {

        super(v);

        AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(v.getContext());
        asyncLayoutInflater.inflate(R.layout.letter_card, v, (view, resid, parent) -> {

            textView = view.findViewById(R.id.characterTextView);
            v.addView(view);
            textView.setText(text);
        });
    }

    public void bindTo(Letter letter) {

        if (textView != null) {
            textView.setText(letter.getCharacter().toString());
        }
        text = letter.getCharacter().toString();
    }
}
