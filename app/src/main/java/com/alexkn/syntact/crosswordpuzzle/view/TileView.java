package com.alexkn.syntact.crosswordpuzzle.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
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

public class TileView extends AppCompatEditText {

    private static final Integer COLOR_SOLVED = Color.argb(64, 0, 255, 0);
    private static final Integer COLOR_UNSOLVED = Color.TRANSPARENT;

    private static final Integer COLOR_FOCUSED = Color.argb(64, 255, 255, 0);
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
        canvas.drawLine(0, 0, getWidth() / 4, 0, paint);
        canvas.drawLine(0, 0, 0, getHeight() / 4, paint);
        canvas.drawLine(getWidth(), getHeight(), getWidth() - getWidth() / 4, getHeight(), paint);
        canvas.drawLine(getWidth(), getHeight(), getWidth(), getHeight() - getHeight() / 4, paint);
        super.onDraw(canvas);

    }

    public void setFocused(boolean focused) {
        if (focused) {
            getBackground().setColorFilter(COLOR_FOCUSED, PorterDuff.Mode.ADD);
        } else {
            getBackground().clearColorFilter();
        }
    }

    public void setColor(Integer color) {
        int currentColor = ((ColorDrawable) getBackground()).getColor();
        ObjectAnimator colorFade = ObjectAnimator.ofObject(this, "backgroundColor", new ArgbEvaluator(), currentColor, color);
        colorFade.setDuration(200);
        colorFade.start();
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

    public void setSolved(Boolean solved) {
        if (solved) {
            setColor(COLOR_SOLVED);
        } else {
            setColor(COLOR_UNSOLVED);
        }

    }
}
