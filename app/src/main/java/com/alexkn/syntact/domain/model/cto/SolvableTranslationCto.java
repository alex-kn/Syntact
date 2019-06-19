package com.alexkn.syntact.domain.model.cto;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

import com.alexkn.syntact.domain.common.Identifiable;
import com.alexkn.syntact.domain.model.Clue;
import com.alexkn.syntact.domain.model.SolvableItem;

import java.util.Objects;

public class SolvableTranslationCto implements Identifiable {

    @NonNull
    @Embedded
    private SolvableItem solvableItem;

    @NonNull
    @Embedded
    private Clue clue;

    @NonNull
    public SolvableItem getSolvableItem() {

        return solvableItem;
    }

    public void setSolvableItem(@NonNull SolvableItem solvableItem) {

        this.solvableItem = solvableItem;
    }

    @NonNull
    public Clue getClue() {

        return clue;
    }

    public void setClue(@NonNull Clue clue) {

        this.clue = clue;
    }

    @Override
    public Long getId() {

        return solvableItem.getId();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolvableTranslationCto that = (SolvableTranslationCto) o;
        return solvableItem.equals(that.solvableItem) && clue.equals(that.clue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(solvableItem, clue);
    }
}
