package com.alexkn.syntact.crosswordpuzzle.model;

import java.util.List;

public class PlacingData {

    public List<Tile> tiles;
    public Phrase phrase;

    public PlacingData(List<Tile> tiles, Phrase phrase) {
        this.tiles = tiles;
        this.phrase = phrase;
    }
}
