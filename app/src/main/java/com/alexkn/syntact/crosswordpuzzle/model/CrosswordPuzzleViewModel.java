package com.alexkn.syntact.crosswordpuzzle.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CrosswordPuzzleViewModel extends ViewModel {

    private LinkedList<Tile> tilesList = new LinkedList<>();

    private MutableLiveData<Set<Tile>> tilesData = new MutableLiveData<>();

    public CrosswordPuzzleViewModel() {
        LinkedList<Phrase> phrases = new LinkedList<>();
        phrases.add(new Phrase("Massive", "ACTION"));
        phrases.add(new Phrase("Massive", "CLOWN"));
        phrases.add(new Phrase("Massive", "OWEN"));
        phrases.add(new Phrase("Massive", "BEER"));
        phrases.add(new Phrase("Massive", "TEA"));
        phrases.add(new Phrase("Massive", "ALL"));
        for (Phrase next : phrases) {
            new PhrasePlacer(tiles -> {
                tilesData.setValue(tiles);
            }).execute(new PlacingData(tilesList, next));
        }
    }

    private void sortTilesByDistanceToOrigin() {

    }

    public MutableLiveData<Set<Tile>> getTilesData() {
        return tilesData;
    }
}