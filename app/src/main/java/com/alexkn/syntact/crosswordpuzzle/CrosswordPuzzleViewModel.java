package com.alexkn.syntact.crosswordpuzzle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

class CrosswordPuzzleViewModel extends ViewModel {

    private LinkedList<Tile> tiles = new LinkedList<>();

    private WorkManager workManager;

    private MutableLiveData<List<Tile>> tilesData = new MutableLiveData<>();

    public CrosswordPuzzleViewModel() {
        LinkedList<Phrase> phrases = new LinkedList<>();
        phrases.add(new Phrase("Massive", "ACTION"));
        phrases.add(new Phrase("Massive", "CLOWN"));
        phrases.add(new Phrase("Massive", "OWEN"));
        phrases.add(new Phrase("Massive", "BEER"));
        phrases.add(new Phrase("Massive", "TEA"));
        phrases.add(new Phrase("Massive", "ALL"));
        for (Phrase next : phrases) {
            new PhrasePlacer(tiles -> tilesData.setValue(tiles)).execute(new PlacingData(tiles, next));
        }

    }

    private void sortTilesByDistanceToOrigin() {

    }







    private void publishTiles() {
        tilesData.postValue(tiles);
    }



    public LiveData<List<Tile>> getTilesData() {
        return tilesData;
    }
}