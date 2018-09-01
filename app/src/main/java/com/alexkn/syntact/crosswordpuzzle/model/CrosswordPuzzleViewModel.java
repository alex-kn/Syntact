package com.alexkn.syntact.crosswordpuzzle.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CrosswordPuzzleViewModel extends ViewModel {

    private TreeMap<Point, Tile> tilesList;

    private MutableLiveData<Set<Tile>> tilesData = new MutableLiveData<>();

    public CrosswordPuzzleViewModel() {
        tilesList = new TreeMap<>(Comparator.comparing(Point::getX).thenComparing(Point::getY));
        LinkedList<Phrase> phrases = new LinkedList<>();
        phrases.add(new Phrase("Massive", "ACTION"));
        phrases.add(new Phrase("Massive", "CLOWN"));
        phrases.add(new Phrase("Massive", "OWEN"));
        phrases.add(new Phrase("Massive", "BEER"));
        phrases.add(new Phrase("Massive", "TEA"));
        phrases.add(new Phrase("Massive", "ALL"));
        for (Phrase next : phrases) {
            new PhrasePlacer(tiles -> tilesData.setValue(new HashSet<>(tiles.values()))).execute(new PlacingData(tilesList, next));
        }
    }

    private void sortTilesByDistanceToOrigin() {

    }

    public MutableLiveData<Set<Tile>> getTilesData() {
        return tilesData;
    }
}