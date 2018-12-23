package com.alexkn.syntact.crosswordpuzzle.model;

import android.arch.lifecycle.MutableLiveData;

import com.alexkn.syntact.crosswordpuzzle.common.Direction;

import java.util.HashSet;
import java.util.Set;


public class DirectionLiveData extends MutableLiveData<Set<Direction>> {

    private Set<Direction> directions = new HashSet<>();

    public void addDirection(Direction direction){
        directions.add(direction);
        postValue(directions);
    }
}
