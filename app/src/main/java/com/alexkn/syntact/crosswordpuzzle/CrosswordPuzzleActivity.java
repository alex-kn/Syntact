package com.alexkn.syntact.crosswordpuzzle;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.widget.Button;

import com.alexkn.syntact.R;

public class CrosswordPuzzleActivity extends AppCompatActivity {

    int boardSize = 40;
    int offset = 20;

    private GridLayout gridLayout;

    private CrosswordPuzzleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossword_puzzle);


        gridLayout = findViewById(R.id.boardGridLayout);
        gridLayout.setRowCount(boardSize);
        gridLayout.setColumnCount(boardSize);
        viewModel = ViewModelProviders.of(this).get(CrosswordPuzzleViewModel.class);

        viewModel.getTilesData().observe(this, tiles -> {
            for (Tile tile : tiles) {
                addPointToGrid(tile);
            }
        });
    }

    public void addPointToGrid(Tile tile) {
        int x = tile.x + offset;
        int y = tile.y + offset;
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(y), GridLayout.spec(x));
        Button button = new Button(this);
        button.setMinimumHeight(120);
        button.setHeight(120);
        button.setMinimumWidth(120);
        button.setWidth(120);
        button.setText(String.valueOf(tile.getCharacter()));
        gridLayout.addView(button, params);

        //TODO extract and save Buttons in subclassed Layout
    }
}
