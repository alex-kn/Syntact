package com.alexkn.syntact.crosswordpuzzle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alexkn.syntact.crosswordpuzzle.model.Direction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TileView extends AppCompatEditText{

    private Paint paint;

    private Set<Direction> openDirections = new HashSet<>();
    private Set<Direction> closedDirections = new HashSet<>();

    public TileView(Context context) {
        super(context);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);


        setInputType(InputType.TYPE_NULL);

        setPrivateImeOptions("nm, com.google.android.inputmethod.latin.noMicrophoneKey");
        setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(1)
        });

        setImeOptions(EditorInfo.IME_ACTION_NEXT);

        setBackgroundColor(Color.TRANSPARENT);
        setHighlightColor(Color.TRANSPARENT);
        setSingleLine();
        setHorizontallyScrolling(false);
        setGravity(Gravity.CENTER);
        setSelectAllOnFocus(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawLine(0, 0, getWidth()/1.2f, 0, paint);
//        canvas.drawLine(0, 0, 0, getHeight()/1.2f, paint);
        canvas.drawLine(0, 0, getWidth()/4, 0, paint);
        canvas.drawLine(0, 0, 0, getHeight()/4, paint);
        canvas.drawLine(getWidth(), getHeight(), getWidth() - getWidth()/4, getHeight(), paint);
        canvas.drawLine(getWidth(), getHeight(), getWidth(), getHeight() - getHeight()/4, paint);
        super.onDraw(canvas);

    }

    public void setColor(Integer color) {
//        getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        setBackgroundColor(color);
    }

    public void setOpenDirections(Set<Direction> openDirections) {
        this.openDirections = openDirections;
        closedDirections = new HashSet<>(Arrays.asList(Direction.values()));
        closedDirections.removeAll(openDirections);
        invalidate();
    }

    public void setCurrentCharacter(Character character) {
        setText(String.valueOf(character).toUpperCase());

    }

    private class TileTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            if (!text.equals(text.toUpperCase())) {
                text = text.toUpperCase();
                setText(text);
            }
        }
    }
}
