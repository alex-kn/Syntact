package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.domain.common.Identifiable;

import java.util.Locale;
import java.util.Objects;

@Entity(indices = @Index("id"))
public class Bucket implements Identifiable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private Integer dailyAverage;

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
    public Integer getDailyAverage() {

        return dailyAverage;
    }

    public void setDailyAverage(@NonNull Integer dailyAverage) {

        this.dailyAverage = dailyAverage;
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
        Bucket bucket = (Bucket) o;
        return Objects.equals(id, bucket.id) && dailyAverage.equals(bucket.dailyAverage) &&
                streak.equals(bucket.streak);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dailyAverage, streak);
    }
}
