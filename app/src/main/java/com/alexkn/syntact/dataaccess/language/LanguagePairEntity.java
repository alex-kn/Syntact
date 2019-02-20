package com.alexkn.syntact.dataaccess.language;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "LanguagePair")
public class LanguagePairEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    private Locale languageLeft;

    @NonNull
    private Locale languageRight;

    @NonNull
    private Integer score;

    public LanguagePairEntity() {

    }

    @Ignore
    public LanguagePairEntity(Integer id, @NonNull Locale languageLeft,
            @NonNull Locale languageRight, Integer score) {

        this.id = id;
        this.languageLeft = languageLeft;
        this.languageRight = languageRight;
        this.score = score;
    }

    public Integer getScore() {

        return score;
    }

    public void setScore(Integer score) {

        this.score = score;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

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
