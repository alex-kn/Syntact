package com.alexkn.syntact.crosswordpuzzle.model;

import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class PlacingData {

    public ConcurrentSkipListMap<Point, Tile> tiles;
    public Phrase phrase;

    public PlacingData(ConcurrentSkipListMap<Point, Tile> tiles, Phrase phrase) {
        this.tiles = tiles;
        this.phrase = phrase;
    }
}
