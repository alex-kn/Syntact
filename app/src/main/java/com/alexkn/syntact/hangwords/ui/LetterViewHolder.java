package com.alexkn.syntact.hangwords.ui;

import android.content.ClipData;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;

class LetterViewHolder extends ListItemViewHolder<Letter> {


    private TextView textView;

    LetterViewHolder(View v) {
        super(v);
        textView = v.findViewById(R.id.characterTextView);
        textView.setMinHeight(textView.getMeasuredWidth());
    }



    void bindTo(Letter letter) {
        textView.setText(letter.getCharacter().toString());
    }




}
