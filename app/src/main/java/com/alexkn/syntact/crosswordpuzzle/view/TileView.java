package com.alexkn.syntact.crosswordpuzzle.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class TileView extends AppCompatButton implements Observer<Integer> {

    public TileView(Context context) {
        super(context);

    }

    @Override
    public void onChanged(@Nullable Integer color) {
        this.setBackgroundColor(color);
    }



}
