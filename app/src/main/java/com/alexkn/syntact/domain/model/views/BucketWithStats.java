package com.alexkn.syntact.domain.model.views;

import androidx.room.DatabaseView;

import com.alexkn.syntact.domain.model.Bucket;

import java.util.Objects;
@DatabaseView("SELECT bucket.*, count(st.id) as numberOfItems from bucket join solvabletranslation st on bucket.id = st.bucketId")
public class BucketWithStats extends Bucket {

    private Integer numberOfItems;

    public Integer getNumberOfItems() {

        return numberOfItems;
    }

    public void setNumberOfItems(Integer numberOfItems) {

        this.numberOfItems = numberOfItems;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BucketWithStats that = (BucketWithStats) o;
        return Objects.equals(numberOfItems, that.numberOfItems);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), numberOfItems);
    }
}
