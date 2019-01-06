package com.alexkn.syntact.hangwords.ui.list;

import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.hangwords.ui.util.Letter;

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