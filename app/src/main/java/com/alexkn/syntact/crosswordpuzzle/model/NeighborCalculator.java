package com.alexkn.syntact.crosswordpuzzle.model;

import java.util.List;
import java.util.stream.Collectors;

public class NeighborCalculator {


    private List<Tile> tiles;

    public NeighborCalculator(List<Tile> tiles) {
        this.tiles = tiles;
    }

    private void calculateNeighbors(){
        for (Tile tile : tiles) {


//            List<Tile> list = tiles.stream().filter(item -> (item.x == coordinates[0] && item.y == coordinates[1])).collect(Collectors.toList());

        }

    }
}
