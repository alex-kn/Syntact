package com.alexkn.syntact.data.model.views;

import androidx.room.DatabaseView;

import com.alexkn.syntact.data.common.Identifiable;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

@DatabaseView(
        "SELECT b.id, b.language, b.createdAt,b.itemCount, (SELECT count(*) FROM solvableitem s JOIN bucket b  ON s.bucketId = b.id WHERE s.timesSolved > 0) as solvedCount FROM Bucket b;")
public class BucketDetail implements Identifiable {

    private Long id;

    private Locale language;

    private Instant createdAt;

    private Integer itemCount;

    private Integer solvedCount;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Locale getLanguage() {

        return language;
    }

    public void setLanguage(Locale language) {

        this.language = language;
    }

    public Instant getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {

        this.createdAt = createdAt;
    }

    public Integer getItemCount() {

        return itemCount;
    }

    public void setItemCount(Integer itemCount) {

        this.itemCount = itemCount;
    }

    public Integer getSolvedCount() {

        return solvedCount;
    }

    public void setSolvedCount(Integer solvedCount) {

        this.solvedCount = solvedCount;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BucketDetail that = (BucketDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(language, that.language) && Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(itemCount, that.itemCount) && Objects.equals(solvedCount, that.solvedCount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, language, createdAt, itemCount, solvedCount);
    }
}
