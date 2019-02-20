package com.alexkn.syntact.dataaccess.language;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "LanguagePair")
public class LanguagePairEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private Locale languageLeft;

    @NonNull
    private Locale languageRight;

    @NonNull
    private int score;

    public LanguagePairEntity() {

    }

    @Ignore
    public LanguagePairEntity(int id, @NonNull Locale languageLeft,
            @NonNull Locale languageRight, int score) {

        this.id = id;
        this.languageLeft = languageLeft;
        this.languageRight = languageRight;
        this.score = score;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {

        this.score = score;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    @NonNull
    public Locale getLanguageLeft() {

        return languageLeft;
    }

    public void setLanguageLeft(@NonNull Locale languageLeft) {

        this.languageLeft = languageLeft;
    }

    @NonNull
    public Locale getLanguageRight() {

        return languageRight;
    }

    public void setLanguageRight(@NonNull Locale languageRight) {

        this.languageRight = languageRight;
    }
}
