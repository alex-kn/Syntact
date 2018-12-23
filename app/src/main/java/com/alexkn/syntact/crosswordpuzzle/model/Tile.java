package com.alexkn.syntact.crosswordpuzzle.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.graphics.Path;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Tile {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;

    private static Direction preferredDirection = Direction.EAST;

    private int x;
    private int y;

    private HashMap<Direction, Tile> neighbors = new HashMap<>();

    private Character correctCharacter;

    private MutableLiveData<Character> currentCharacter = new MutableLiveData<>();

    private MediatorLiveData<Boolean> phraseSolved = new MediatorLiveData<>();

    private MutableLiveData<Integer> color = new MutableLiveData<>();

    private DirectionLiveData openDirections = new DirectionLiveData();

    private TreeMap<Axis, Phrase> phrases = new TreeMap<>();

    private MutableLiveData<Boolean> focused = new MutableLiveData<>();
    private MediatorLiveData<Boolean> connected = new MediatorLiveData<>();

    private final int maxRegistrations = 2;

    Tile(int x, int y) {
        this.id = count.incrementAndGet();
        this.x = x;
        this.y = y;
        phraseSolved.addSource(currentCharacter, character -> checkPhrasesSolved());
        connected.addSource(focused, connected -> {
            for (Phrase phrase : phrases.values()) {
                phrase.highlightTiles(connected);
            }

        });
    }

    Tile(int[] coordinates) {
        this(coordinates[0], coordinates[1]);
    }

    public void register(Phrase phrase, Axis axis) {
        if (phrases.size() <= maxRegistrations) {
            phrases.put(axis, phrase);
            phrase.addTile(this);
            phraseSolved.addSource(phrase.isSolved(), aBoolean -> {
                for (Phrase phrase1 : phrases.values()) {
                    //noinspection ConstantConditions
                    if (phrase1.isSolved().getValue()) {
                        phraseSolved.postValue(true);
                        return;
                    }
                }
                phraseSolved.postValue(false);

            });
        }
    }

    public void setInput(Character character) {
        currentCharacter.postValue(character);
    }

    public boolean hasCorrectCharacter() {
        Character currentChar = currentCharacter.getValue();
        return currentChar == correctCharacter;
    }

    private void checkPhrasesSolved() {
        for (Phrase phrase : phrases.values()) {
            phrase.checkSolved();
        }
    }

    private List<Tile> findTilesWithSamePhrase(Direction direction) {
        List<Tile> tiles = new LinkedList<>();

        Tile tile = neighbors.get(direction);
        if (tile == null) return tiles;
        for (Phrase phrase : tile.getPhrases().values()) {
            for (Phrase phrase1 : this.phrases.values()) {
                if (phrase.equals(phrase1)) {
                    tiles.addAll(tile.findTilesWithSamePhrase(direction));
                    tiles.add(tile);
                }
            }
        }
        return tiles;
    }

    public ArrayList<Axis> getFreeAxis() {
        ArrayList<Axis> axes = new ArrayList<>();
        if (phrases.isEmpty()) {
            axes.add(Axis.X);
            axes.add(Axis.Y);
        } else {
            axes.add(phrases.firstKey().opposite());
        }
        return axes;
    }

    public boolean isAxisFree(Axis axis) {
        return !phrases.containsKey(axis);
    }

    public Character getCorrectCharacter() {
        return correctCharacter;
    }

    public void setCorrectCharacter(Character correctCharacter) {
        this.correctCharacter = correctCharacter;
    }

    public boolean canHaveCharacter(Character character) {
        return this.correctCharacter == null || this.correctCharacter.equals(character);
    }

    public void setNeighbor(Direction direction, Tile tile) {
        neighbors.put(direction, tile);
        if (!findTilesWithSamePhrase(direction).isEmpty()) {
            openDirections.addDirection(direction);
        }
    }

    public Optional<Tile> getNext() {
        Tile tile1 = neighbors.get(preferredDirection);
        if (tile1 != null) {
            return Optional.of(tile1);
        }
        Tile tile2 = neighbors.get(Direction.EAST);
        if (tile2 != null) {
            preferredDirection = Direction.EAST;
            return Optional.of(tile2);
        }
        Tile tile3 = neighbors.get(Direction.SOUTH);
        if (tile3 != null) {
            preferredDirection = Direction.SOUTH;
            return Optional.of(tile3);
        }
        return Optional.empty();
    }

    public boolean isFull() {
        return phrases.size() >= maxRegistrations;
    }

    public int[] getCoordinates() {
        return new int[]{x, y};
    }

    public void setColor(Integer color) {
        this.color.setValue(color);
    }

    public LiveData<Integer> getColor() {
        return color;
    }

    public LiveData<Character> getCurrentCharacter() {
        return currentCharacter;
    }

    public LiveData<Boolean> isPhraseSolved() {
        return phraseSolved;
    }

    public LiveData<Set<Direction>> getOpenDirections() {
        return openDirections;
    }

    public LiveData<Boolean> isFocused() {
        return focused;
    }

    public void setFocused(Boolean value) {
        focused.postValue(value);
    }

    public LiveData<Boolean> isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected.postValue(connected);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void solveHorizontal() {
        phrases.get(Axis.X).solve();
    }

    public void solveVertical() {
        phrases.get(Axis.Y).solve();
    }

    public void solve() {
        currentCharacter.postValue(correctCharacter);
    }

    public TreeMap<Axis, Phrase> getPhrases() {
        return phrases;
    }

    public Optional<String> getHorizontalClue() {
        Phrase phrase = phrases.get(Axis.X);
        if (phrase != null) {
            return Optional.of(phrase.getClue());
        }
        return Optional.empty();
    }

    public Optional<String> getVerticalClue() {
        Phrase phrase = phrases.get(Axis.Y);
        if (phrase != null) {
            return Optional.of(phrase.getClue());
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return x == tile.x &&
                y == tile.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
