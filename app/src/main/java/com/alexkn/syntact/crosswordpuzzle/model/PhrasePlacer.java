package com.alexkn.syntact.crosswordpuzzle.model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PhrasePlacer extends AsyncTask<PlacingData, Void, Set<Tile>> {

    public static final String KEY_TILES = "T";
    public static final String KEY_PHRASE = "P";

    public static final String KEY_RESULT = "R";

    private List<Tile> tiles;
    private Set<Tile> newTiles;
    private Phrase phrase;

    private TaskCompleteCallback callback;

    private boolean success;

    public PhrasePlacer(TaskCompleteCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Set<Tile> doInBackground(PlacingData... placingData) {

        tiles = placingData[0].tiles;
        newTiles = new HashSet<>();
        addPhraseToGrid(placingData[0].phrase);

        return newTiles;
    }

    private void addPhraseToGrid(Phrase phrase) {
        for (int i = 0; i < tiles.size(); i++) {
            Tile currentTile = tiles.get(i);
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
            if (!tiles.contains(tile)) {
                newTiles.add(tile);
                tiles.add(tile);
            }
        }
        success = true;
    }

    private Tile getTileAt(int[] coordinates) {
        List<Tile> list = tiles.stream().filter(item -> (item.x == coordinates[0] && item.y == coordinates[1])).collect(Collectors.toList());
        if (list.isEmpty()) {
            return new Tile(coordinates);
        } else {
            return list.get(0);
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
            if (!tiles.contains(new Tile(i, j))) {
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
    protected void onPostExecute(Set<Tile> set) {
        super.onPostExecute(set);
        callback.onTaskComplete(set);
    }

    public interface TaskCompleteCallback {
        void onTaskComplete(Set<Tile> tiles);
    }
}
