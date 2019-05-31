package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.domain.common.Identifiable;

import java.util.Locale;
import java.util.Objects;

import static androidx.room.ForeignKey.SET_NULL;

@Entity
        (indices = {@Index("id"), @Index("templateId")},
        foreignKeys = @ForeignKey(entity = Template.class, parentColumns = "id",
                childColumns = "templateId", onDelete = SET_NULL))
public class Bucket implements Identifiable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private Integer dailyAverage;

    @NonNull
    private Integer streak;

    @NonNull
    private Locale language;

    @NonNull
    private Boolean ready;

    @NonNull
    private Locale userLanguage;

    private Integer templateId;

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

    @NonNull
    public Locale getLanguage() {

        return language;
    }

    public void setLanguage(@NonNull Locale language) {

        this.language = language;
    }

    @NonNull
    public Boolean getReady() {

        return ready;
    }

    public void setReady(@NonNull Boolean ready) {

        this.ready = ready;
    }

    @NonNull
    public Locale getUserLanguage() {

        return userLanguage;
    }

    public void setUserLanguage(@NonNull Locale userLanguage) {

        this.userLanguage = userLanguage;
    }

    public Integer getTemplateId() {

        return templateId;
    }

    public void setTemplateId(Integer templateId) {

        this.templateId = templateId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bucket bucket = (Bucket) o;
        return Objects.equals(id, bucket.id) && dailyAverage.equals(bucket.dailyAverage) &&
                streak.equals(bucket.streak) && language.equals(bucket.language) &&
                ready.equals(bucket.ready) && userLanguage.equals(bucket.userLanguage) &&
                Objects.equals(templateId, bucket.templateId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dailyAverage, streak, language, ready, userLanguage, templateId);
    }
}
