package com.alexkn.syntact.dataaccess.language;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Locale;

@Entity(tableName = "LanguagePair", indices = @Index("id"))
public class LanguagePairEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private Locale languageLeft;

    @NonNull
    private Locale languageRight;

    @NonNull
    private Integer score;

    @NonNull
    private Integer level;

    @NonNull
    private Integer streak;

    public LanguagePairEntity() {

    }

    @Ignore
    public LanguagePairEntity(Long id, @NonNull Locale languageLeft, @NonNull Locale languageRight,
            Integer score, @NonNull Integer level, @NonNull Integer streak) {

        this.id = id;
        this.languageLeft = languageLeft;
        this.languageRight = languageRight;
        this.score = score;
        this.level = level;
        this.streak = streak;
    }

    public Integer getScore() {

        return score;
    }

    public void setScore(Integer score) {

        this.score = score;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

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

    @NonNull
    public Integer getLevel() {

        return level;
    }

    public void setLevel(@NonNull Integer level) {

        this.level = level;
    }

    @NonNull
    public Integer getStreak() {

        return streak;
    }

    public void setStreak(@NonNull Integer streak) {

        this.streak = streak;
    }
}
