package com.alexkn.syntact.hangwords.ui;

import android.content.ClipData;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.alexkn.syntact.R;

class LetterViewHolder extends EntityViewHolder<Letter> {


    private TextView textView;

    LetterViewHolder(View v) {
        super(v);
        textView = v.findViewById(R.id.characterTextView);
        textView.setMinHeight(textView.getMeasuredWidth());
    }



    void bindTo(Letter letter) {

        textView.setText(letter.getCharacter().toString());

        itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startDragAndDrop(ClipData.newPlainText(letter.toString(), letter.toString()),
                            new LetterDragShadow(v), null, 0);
                }
                return false;
            }
        });
    }

    private class LetterDragShadow extends View.DragShadowBuilder{

        LetterDragShadow(View view) {
            super(view);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            View v = getView();
            int height = v.getHeight();
            int width = v.getWidth();
            v.setAlpha(1f);
            shadowSize.set(width, height);
            shadowTouchPoint.set((width / 2), height*2);
        }
    }
}
