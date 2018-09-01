package com.alexkn.syntact.crosswordpuzzle;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.View;

import com.alexkn.syntact.R;
import com.alexkn.syntact.crosswordpuzzle.model.CrosswordPuzzleViewModel;
import com.alexkn.syntact.crosswordpuzzle.model.Tile;
import com.alexkn.syntact.crosswordpuzzle.view.TileView;

import java.util.HashSet;
import java.util.Set;

public class CrosswordPuzzleActivity extends AppCompatActivity {

    int boardSize = 40;
    int offset = 20;

    private GridLayout gridLayout;

    private CrosswordPuzzleViewModel viewModel;

    private Set<Integer> addedTiles;

    private Set<Tile> currentTiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossword_puzzle);

        addedTiles = new HashSet<>();
        gridLayout = findViewById(R.id.boardGridLayout);
        gridLayout.setRowCount(boardSize);
        gridLayout.setColumnCount(boardSize);

        viewModel = ViewModelProviders.of(this).get(CrosswordPuzzleViewModel.class);
        viewModel.getTilesData().observe(this, tiles -> {
            for (Tile tile : tiles) {
                addTileToGrid(tile);
            }
        });
    }

    public void addTileToGrid(Tile tile) {
        if(addedTiles.contains(tile.getId())) return;
        addedTiles.add(tile.getId());
        int x = tile.getX() + offset;
        int y = tile.getY() + offset;
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(y), GridLayout.spec(x));

        TileView tileView = new TileView(this);
        tileView.setMinimumHeight(120);
        tileView.setHeight(120);
        tileView.setMinimumWidth(120);
        tileView.setWidth(120);
        tileView.setText(String.valueOf(tile.getCharacter()));
        tile.getColor().observe(this, tileView);

        tileView.setOnClickListener(view -> tile.setColorForNeighbors(Color.GREEN));

        gridLayout.addView(tileView, params);
    }
}
