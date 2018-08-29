package com.alexkn.syntact.crosswordpuzzle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class Grid {

    private LinkedList<Tile> points = new LinkedList<>();
    private CrosswordPuzzleActivity activity;

    public Grid(CrosswordPuzzleActivity activity) {
        this.activity = activity;
        LinkedList<Phrase> phrases = new LinkedList<>();
        phrases.add(new Phrase("Massive", "ACTION"));
        phrases.add(new Phrase("Massive", "CLOWN"));
        phrases.add(new Phrase("Massive", "OWEN"));
        phrases.add(new Phrase("Massive", "BEER"));
        phrases.add(new Phrase("Massive", "TEA"));
        phrases.add(new Phrase("Massive", "ALL"));
        for (Phrase next : phrases) {
            addPhraseToGrid(next);
        }
    }

    private void sortPointsByDistanceToOrigin() {

    }

    public void addPhraseToGrid(Phrase phrase) {
        for (int i = 0; i < points.size(); i++) {
            Tile currentPoint = points.get(i);
            if (!currentPoint.isFull()) {
                Character intersectingCharacter = currentPoint.getCharacter();
                if (phrase.hasCharacter(intersectingCharacter)) {
                    ArrayList<Integer> intersectingCharacterIndexes = phrase.getIndexOf(intersectingCharacter);

                    for (Integer intersectingCharacterIndex : intersectingCharacterIndexes) {
                        if (addPhraseToGrid(phrase, currentPoint, intersectingCharacterIndex)) {
                            return;
                        }
                    }
                }
            }
        }


        findNewPointFor(phrase);
    }

    private void findNewPointFor(Phrase phrase) {
        int maxTries = 20;
        for (int i = 0; i < maxTries; i++) {
            Tile point = findEmptyPoint();
            for (int j = 0; j < phrase.getLength(); j++) {
                if (addPhraseToGrid(phrase, point, i)) {
                    return;
                }
            }
        }
    }

    private boolean addPhraseToGrid(Phrase phrase, Tile currentPoint, int intersectingCharacterIndex) {
        ArrayList<Axis> freeAxes = currentPoint.getFreeAxis();
        for (Axis freeAxis : freeAxes) {
            boolean successful = true;
            LinkedList<Tile> pointsToRegister = new LinkedList<>();
            int[] coordinates = currentPoint.getCoordinates();
            coordinates[freeAxis.index()] -= intersectingCharacterIndex;

            for (int j = 0; j < phrase.getLength(); j++) {
                Character character = phrase.getCharacterAt(j);
                Tile point = getPointAt(coordinates);
                pointsToRegister.add(point);
                coordinates[freeAxis.index()]++;
                if (!(point.canHaveCharacter(character) && point.isAxisFree(freeAxis))) {
                    successful = false;
                    break;
                }
            }
            if (successful) {
                addPhrase(phrase, freeAxis, pointsToRegister);
                return true;
            }
        }
        return false;
    }

    private void addPhrase(Phrase phrase, Axis freeAxis, LinkedList<Tile> pointsToRegister) {
        for (int i = 0; i < pointsToRegister.size(); i++) {
            Tile point = pointsToRegister.get(i);
            point.setCharacter(phrase.getCharacterAt(i));
            point.register(phrase, freeAxis);
            if (!points.contains(point)) {
                addNewPoint(point);
                points.add(point);
            }
        }
    }

    private Tile getPointAt(int[] coordinates) {
        List<Tile> list = points.stream().filter(item -> (item.x == coordinates[0] && item.y == coordinates[1])).collect(Collectors.toList());
        if (list.isEmpty()) {
            return new Tile(coordinates);
        } else {
            return list.get(0);
        }


    }

    private void addNewPoint(Tile point) {
        activity.addPointToGrid(point);
    }

    private Tile findEmptyPoint() {
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
            if (!points.contains(new Tile(i, j))) {
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
}