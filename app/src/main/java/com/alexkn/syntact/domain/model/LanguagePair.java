package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.domain.common.Identifiable;

import java.util.Locale;
import java.util.Objects;

@Entity(indices = @Index("id"))
public class LanguagePair implements Identifiable {

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

    @Override
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
    public Integer getScore() {

        return score;
    }

    public void setScore(@NonNull Integer score) {

        this.score = score;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguagePair that = (LanguagePair) o;
        return Objects.equals(id, that.id) && languageLeft.equals(that.languageLeft) &&
                languageRight.equals(that.languageRight) && score.equals(that.score) &&
                level.equals(that.level) && streak.equals(that.streak);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, languageLeft, languageRight, score, level, streak);
    }
}
