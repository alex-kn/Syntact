package com.alexkn.syntact.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.data.common.Identifiable;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

@Entity
public class Bucket implements Identifiable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private Locale language;

    @NonNull
    private Instant createdAt;

    @NonNull
    private Locale userLanguage;

    @NonNull
    private String phrasesUrl;

    @NonNull
    private Integer itemCount;

    @Override
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @NonNull
    public Locale getLanguage() {

        return language;
    }

    public void setLanguage(@NonNull Locale language) {

        this.language = language;
    }

    @NonNull
    public Instant getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(@NonNull Instant createdAt) {

        this.createdAt = createdAt;
    }

    @NonNull
    public Locale getUserLanguage() {

        return userLanguage;
    }

    public void setUserLanguage(@NonNull Locale userLanguage) {

        this.userLanguage = userLanguage;
    }

    @NonNull
    public String getPhrasesUrl() {

        return phrasesUrl;
    }

    public void setPhrasesUrl(@NonNull String phrasesUrl) {

        this.phrasesUrl = phrasesUrl;
    }

    @NonNull
    public Integer getItemCount() {

        return itemCount;
    }

    public void setItemCount(@NonNull Integer itemCount) {

        this.itemCount = itemCount;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bucket bucket = (Bucket) o;
        return Objects.equals(id, bucket.id) && language.equals(bucket.language) && createdAt.equals(bucket.createdAt) &&
                userLanguage.equals(bucket.userLanguage) && phrasesUrl.equals(bucket.phrasesUrl) && itemCount.equals(bucket.itemCount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, language, createdAt, userLanguage, phrasesUrl, itemCount);
    }
}