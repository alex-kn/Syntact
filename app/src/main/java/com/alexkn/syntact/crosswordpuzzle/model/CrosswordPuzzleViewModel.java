package com.alexkn.syntact.crosswordpuzzle.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

public class CrosswordPuzzleViewModel extends ViewModel {


    private TreeMap<Point, Tile> tiles;

    private MutableLiveData<Set<Tile>> tilesData = new MutableLiveData<>();

    public CrosswordPuzzleViewModel() {
        tiles = new TreeMap<>(Comparator.comparing(Point::getX).thenComparing(Point::getY));
        LinkedList<Phrase> phrases = new LinkedList<>();
        phrases.add(new Phrase("Massive", "ACTION"));
        phrases.add(new Phrase("Massive", "CLOWN"));
        phrases.add(new Phrase("Massive", "OWEN"));
        phrases.add(new Phrase("Massive", "BEER"));
        phrases.add(new Phrase("Massive", "TEA"));
        phrases.add(new Phrase("Massive", "ALL"));
        phrases.add(new Phrase("Massive", "LEWIS"));
        phrases.add(new Phrase("Massive", "LOW"));
        phrases.add(new Phrase("Massive", "CARTOON"));
        phrases.add(new Phrase("Massive", "WAIT"));
        phrases.add(new Phrase("Massive", "WIGGLE"));
        for (Phrase next : phrases) {
            new PhrasePlacer(tiles -> {
                tilesData.setValue(new HashSet<>(tiles.values()));
                calculateNeighbors();
            }).execute(new PlacingData(tiles, next));
        }

    }

    private void sortTilesByDistanceToOrigin() {

    }

    private void calculateNeighbors() {
        Direction[] neighborDirections = Direction.values();

        for (Tile tile : tiles.values()) {
            for (Direction direction : neighborDirections) {
                Tile neighbor = tiles.get(new Point(tile.getX() + direction.dx(),tile.getY() + direction.dy()));
                if (neighbor != null) {
                    tile.setNeighbor(direction, neighbor);
                }
            }
        }


    }

    public MutableLiveData<Set<Tile>> getTilesData() {
        return tilesData;
    }
}