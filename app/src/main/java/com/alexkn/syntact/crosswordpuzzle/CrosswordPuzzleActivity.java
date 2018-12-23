package com.alexkn.syntact.crosswordpuzzle;

import android.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexkn.syntact.R;
import com.alexkn.syntact.crosswordpuzzle.model.CrosswordPuzzleViewModel;
import com.alexkn.syntact.crosswordpuzzle.model.Tile;
import com.alexkn.syntact.crosswordpuzzle.view.CrosswordPuzzleGridLayout;
import com.alexkn.syntact.crosswordpuzzle.view.TileView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CrosswordPuzzleActivity extends AppCompatActivity {

    private static final String alphabet = "ABCDEFGJKLMNPRSTUVWXYZ";

    private static final int keyboardSize = 6;

    private Tile focusedTile;

    int boardSize = 80;
    int offset = 40;


    private CrosswordPuzzleGridLayout gridLayout;

    private LinearLayout keyboardContainer;
    private TextView verticalClueTextView;
    private TextView horizontalClueTextView;
    private LinearLayout horizontalClueContainer;
    private LinearLayout verticalClueContainer;
    private LinearLayout clueLayout;

    private CrosswordPuzzleViewModel viewModel;

    private Set<Integer> addedTiles;

    private Set<Tile> currentTiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossword_puzzle);

        addedTiles = new HashSet<>();
        keyboardContainer = findViewById(R.id.keyboardContainer);
        keyboardContainer.setGravity(Gravity.CENTER);

        horizontalClueTextView = findViewById(R.id.horizontalClueTextView);
        horizontalClueContainer = findViewById(R.id.horizontalClueContainer);

        verticalClueTextView = findViewById(R.id.verticalClueTextView);
        verticalClueContainer = findViewById(R.id.verticalClueContainer);

        clueLayout = findViewById(R.id.boardClueLayout);
        clueLayout.bringToFront();
        clueLayout.setGravity(Gravity.CENTER);

        gridLayout = findViewById(R.id.boardGridLayout);
        gridLayout.setRowCount(boardSize);
        gridLayout.setColumnCount(5);
        gridLayout.init(getApplicationContext());

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        viewModel = ViewModelProviders.of(this).get(CrosswordPuzzleViewModel.class);
        viewModel.getTilesData().observe(this, tiles -> tiles.forEach(this::addTileToGrid));

        viewModel.nextPhrase();

        getSupportActionBar().hide();

        FloatingActionButton continueButton = findViewById(R.id.fab);
        continueButton.setOnClickListener(this::nextPhrase);

    }

    public void nextPhrase(View view) {
        viewModel.nextPhrase();
    }

    public void addTileToGrid(Tile tile) {
        if (addedTiles.contains(tile.getId())) return;
        addedTiles.add(tile.getId());
        int x = tile.getX();
        int y = tile.getY() + offset;
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(y), GridLayout.spec(x));
        params.setMargins(5, 5, 5, 5);
        TileView tileView = new TileView(this);

        tileView.setMinimumHeight(120);
        tileView.setHeight(120);
        tileView.setMinimumWidth(120);
        tileView.setWidth(120);

        tile.getColor().observe(this, tileView::setColor);
        tile.getOpenDirections().observe(this, tileView::setOpenDirections);
        tile.getCurrentCharacter().observe(this, tileView::setCurrentCharacter);
        tile.isPhraseSolved().observe(this, tileView::setSolved);
        tile.isConnected().observe(this, tileView::setConnected);

        tile.isFocused().observe(this, aBoolean -> {
            if (aBoolean) {
                tileView.requestFocus();
                showClues(tile);
            }
        });

        tileView.setOnFocusChangeListener((v, hasFocus) -> {
            tileView.setFocused(hasFocus);
            tile.setFocused(hasFocus);
            if (hasFocus) {
                focusedTile = tile;
                showKeyboard(tile);
            }
        });
        gridLayout.addView(tileView, params);
        tileView.requestFocus();
        gridLayout.scrollTo(Math.round(tileView.getX()), Math.round(tileView.getY()));
    }

    public void solveHorizontal(View view) {
        focusedTile.solveHorizontal();
    }

    public void solveVertical(View view) {
        focusedTile.solveVertical();
    }

    private void showClues(Tile tile) {
        Optional<String> horizontalClue = tile.getHorizontalClue();
        if (horizontalClue.isPresent()) {
            Toast.makeText(this, horizontalClue.get(), Toast.LENGTH_SHORT).show();
        }
        Optional<String> verticalClue = tile.getVerticalClue();
        if (verticalClue.isPresent()) {
            Toast.makeText(this, verticalClue.get(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showKeyboard(Tile tile) {
        keyboardContainer.removeAllViews();
        List<Character> characters = new LinkedList<>();
        for (int i = 0; i < keyboardSize - 1; i++) {
            SecureRandom rnd = new SecureRandom();
            Character character = alphabet.charAt(rnd.nextInt(alphabet.length()));
            characters.add(character);
        }
        characters.add(tile.getCorrectCharacter());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        for (Character character : characters) {
            Button button = new Button(getApplicationContext());
            button.setOnClickListener(v -> {
                focusedTile.setInput(((Button) v).getText().charAt(0));
                //TODO
//                focusedTile.getNext().ifPresent(tile1 -> {
//                    focusedTile = tile1;
//                    tile1.setFocused(true);
//                });
            });
            button.setText(character.toString());
            button.setLayoutParams(layoutParams);
            keyboardContainer.addView(button);
        }


    }

}
