package com.alexkn.syntact.presentation.bucket.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import com.alexkn.syntact.R;
import com.alexkn.syntact.databinding.BucketListBucketCardBinding;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.presentation.common.ListItemAdapter;
import com.alexkn.syntact.presentation.common.ListItemViewHolder;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;

public class BucketAdapter extends ListItemAdapter<Bucket, BucketAdapter.BucketViewHolder> {

    @NonNull
    @Override
    public BucketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BucketListBucketCardBinding dataBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.bucket_list_bucket_card,
                        parent, false);

        Drawable drawable = ResourcesCompat.getDrawable(parent.getResources(), R.drawable.fr, null);
        dataBinding.setFlag(drawable);


        return new BucketViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BucketViewHolder holder, int position) {

        Bucket bucket = getList().get(position);
        holder.bindTo(bucket);
    }

    static class BucketViewHolder extends ListItemViewHolder<Bucket> {

        final BucketListBucketCardBinding binding;

        BucketViewHolder(BucketListBucketCardBinding dataBinding) {

            super(dataBinding.getRoot());
            this.binding = dataBinding;
        }

        @Override
        public void bindTo(Bucket bucket) {

            Instant created = bucket.getCreatedAt();
//            long days = DAYS.between(created, Instant.now());TODO
//            double average = ((double) bucket.getTotalSolvedCount()) / days;
//            binding.setAverage(Double.toString(average));
//            binding.setSolved(bucket.getTotalSolvedCount().toString());
            binding.setTotal("698");
            binding.setBucket(bucket);
        }
    }
}
