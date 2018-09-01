package com.alexkn.syntact.crosswordpuzzle.model;

import java.util.TreeMap;

public class PlacingData {

    public TreeMap<Point, Tile> tiles;
    public Phrase phrase;

    public PlacingData(TreeMap<Point, Tile> tiles, Phrase phrase) {
        this.tiles = tiles;
        this.phrase = phrase;
    }
}
