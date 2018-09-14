package com.alexkn.syntact.crosswordpuzzle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatButton;

import com.alexkn.syntact.crosswordpuzzle.model.Direction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TileView extends AppCompatButton {

    private Paint paint;

    private Set<Direction> openDirections = new HashSet<>();
    private Set<Direction> closedDirections = new HashSet<>();

    public TileView(Context context) {
        super(context);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(8);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        for (Direction closedDirection : closedDirections) {

            switch (closedDirection) {
                case NORTH:
                    canvas.drawLine(3, 3, getWidth()-3, 3, paint);
                    break;
                case EAST:
                    canvas.drawLine(getWidth()-3, 3, getWidth()-3, getHeight()-3, paint);
                    break;
                case SOUTH:
                    canvas.drawLine(getWidth()-3, getHeight()-3, 3, getHeight()-3, paint);
                    break;
                case WEST:
                    canvas.drawLine(3, getHeight()-3, 3, 3, paint);
                    break;
            }
        }

        super.onDraw(canvas);
    }

    public void setColor(Integer color) {
        getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    public void setOpenDirections(Set<Direction> openDirections) {
        this.openDirections = openDirections;
        closedDirections = new HashSet<>(Arrays.asList(Direction.values()));
        closedDirections.removeAll(openDirections);
        invalidate();
    }
}
