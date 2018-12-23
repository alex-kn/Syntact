package com.alexkn.syntact.crosswordpuzzle.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alexkn.syntact.crosswordpuzzle.common.Direction;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

public class CrosswordPuzzleViewModel extends ViewModel {


    private ConcurrentSkipListMap<Point, Tile> tiles;

    private MutableLiveData<Set<Tile>> tilesData = new MutableLiveData<>();
    private LinkedList<Phrase> phrases  = new LinkedList<>();;

    public CrosswordPuzzleViewModel() {
        tiles = new ConcurrentSkipListMap<>(Comparator.comparing(Point::getX).thenComparing(Point::getY));
        phrases.add(new Phrase("Aktion", "ACTION"));
        phrases.add(new Phrase("Clown", "CLOWN"));
        phrases.add(new Phrase("Owen", "OWEN"));
        phrases.add(new Phrase("Lewis", "LEWIS"));
        phrases.add(new Phrase("Bier", "BEER"));
        phrases.add(new Phrase("Tee", "TEA"));
        phrases.add(new Phrase("Alle", "ALL"));
        phrases.add(new Phrase("Wackeln", "WIGGLE"));
        phrases.add(new Phrase("Warten", "WAIT"));
        phrases.add(new Phrase("Niedrig", "LOW"));
        phrases.add(new Phrase("Zeichentrickfilm", "CARTOON"));
//        for (Phrase next : phrases) {
//            new PhrasePlacer(tiles -> {
//                tilesData.setValue(new HashSet<>(tiles.values()));
//                calculateNeighbors();
//            }).execute(new PlacingData(tiles, next));
//        }

    }

    public void nextPhrase() {
        Phrase phrase = phrases.pop();
        new PhrasePlacer(tiles -> {
            tilesData.setValue(new HashSet<>(tiles.values()));
            calculateNeighbors();
        }).execute(new PlacingData(tiles, phrase));
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
