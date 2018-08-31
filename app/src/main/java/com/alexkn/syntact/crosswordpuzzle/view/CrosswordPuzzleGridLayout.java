package com.alexkn.syntact.crosswordpuzzle.view;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.widget.Button;

import com.alexkn.syntact.crosswordpuzzle.model.Tile;


public class CrosswordPuzzleGridLayout extends GridLayout {

    public CrosswordPuzzleGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CrosswordPuzzleGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CrosswordPuzzleGridLayout(Context context) {
        super(context);
    }
}
