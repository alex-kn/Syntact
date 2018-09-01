package com.alexkn.syntact.crosswordpuzzle.model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class PhrasePlacer extends AsyncTask<PlacingData, Void, TreeMap<Point, Tile>> {

    private TreeMap<Point, Tile> tiles;

    private TaskCompleteCallback callback;

    PhrasePlacer(TaskCompleteCallback callback) {
        this.callback = callback;
    }

    @Override
    protected TreeMap<Point, Tile> doInBackground(PlacingData... placingData) {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tiles = placingData[0].tiles;
        addPhraseToGrid(placingData[0].phrase);

        return tiles;
    }

    private void addPhraseToGrid(Phrase phrase) {
        for (Map.Entry<Point, Tile> entry : tiles.entrySet()){
            Tile currentTile = entry.getValue();
            if (!currentTile.isFull()) {
                Character intersectingCharacter = currentTile.getCharacter();
                if (phrase.hasCharacter(intersectingCharacter)) {
                    ArrayList<Integer> intersectingCharacterIndexes = phrase.getIndexOf(intersectingCharacter);

                    for (Integer intersectingCharacterIndex : intersectingCharacterIndexes) {
                        if (addPhraseToGrid(phrase, currentTile, intersectingCharacterIndex)) {
                            return;
                        }
                    }
                }
            }
        }
        findNewTileFor(phrase);
    }

    private void findNewTileFor(Phrase phrase) {
        int maxTries = 20;
        for (int i = 0; i < maxTries; i++) {
            Tile tile = findEmptyTile();
            for (int j = 0; j < phrase.getLength(); j++) {
                if (addPhraseToGrid(phrase, tile, i)) {
                    return;
                }
            }
        }
    }

    private boolean addPhraseToGrid(Phrase phrase, Tile currentTile, int intersectingCharacterIndex) {
        ArrayList<Axis> freeAxes = currentTile.getFreeAxis();
        for (Axis freeAxis : freeAxes) {
            boolean successful = true;
            LinkedList<Tile> tilesToRegister = new LinkedList<>();
            int[] coordinates = currentTile.getCoordinates();
            coordinates[freeAxis.index()] -= intersectingCharacterIndex;

            for (int j = 0; j < phrase.getLength(); j++) {
                Character character = phrase.getCharacterAt(j);
                Tile tile = getTileAt(coordinates);
                tilesToRegister.add(tile);
                coordinates[freeAxis.index()]++;
                if (!(tile.canHaveCharacter(character) && tile.isAxisFree(freeAxis))) {
                    successful = false;
                    break;
                }
            }
            if (successful) {
                addPhrase(phrase, freeAxis, tilesToRegister);
                return true;
            }
        }
        return false;
    }

    private void addPhrase(Phrase phrase, Axis freeAxis, LinkedList<Tile> tilesToRegister) {
        for (int i = 0; i < tilesToRegister.size(); i++) {
            Tile tile = tilesToRegister.get(i);
            tile.setCharacter(phrase.getCharacterAt(i));
            tile.register(phrase, freeAxis);
            Point key = new Point(tile.getX(), tile.getY());
            if (!tiles.containsKey(key)) {
                tiles.put(key, tile);
            }
        }
    }

    private Tile getTileAt(int[] coordinates) {
        Tile tile = tiles.get(new Point(coordinates[0], coordinates[1]));
        if (tile == null) {
            return new Tile(coordinates);
        } else {
            return tile;
        }
    }

    private Tile findEmptyTile() {
        int iterations = 100;

        // (di, dj) is a vector - direction in which we move right now
        int di = 1;
        int dj = 0;
        // length of current segment
        int segmentLength = 1;

        // current position (i, j) and how much of current segment we passed
        int i = 0;
        int j = 0;
        int segmentPassed = 0;
        for (int k = 0; k < iterations; ++k) {
            // make a step, add 'direction' vector (di, dj) to current position (i, j)
            if (!tiles.containsKey(new Point(i, j))) {
                return new Tile(i, j);
            }
            System.out.println(i + " " + j);
            i += di;
            j += dj;
            ++segmentPassed;

            if (segmentPassed == segmentLength) {
                // done with current segment
                segmentPassed = 0;

                // 'rotate' directions
                int buffer = di;
                di = -dj;
                dj = buffer;

                // increase segment length if necessary
                if (dj == 0) {
                    ++segmentLength;
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    protected void onPostExecute(TreeMap<Point, Tile> list) {
        super.onPostExecute(list);
        callback.onTaskComplete(list);
    }

    public interface TaskCompleteCallback {
        void onTaskComplete(TreeMap<Point, Tile> tiles);
    }
}
