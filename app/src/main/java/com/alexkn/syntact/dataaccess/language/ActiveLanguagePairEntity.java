package com.alexkn.syntact.dataaccess.language;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ActiveLanguagePair")
public class ActiveLanguagePairEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private Locale languageLeft;

    @NonNull
    private Locale languageRight;

    public ActiveLanguagePairEntity() {

    }

    @Ignore
    public ActiveLanguagePairEntity(int id, @NonNull Locale languageLeft,
            @NonNull Locale languageRight) {

        this.id = id;
        this.languageLeft = languageLeft;
        this.languageRight = languageRight;
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
