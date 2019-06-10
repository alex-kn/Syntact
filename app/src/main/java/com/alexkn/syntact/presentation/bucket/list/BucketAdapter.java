package com.alexkn.syntact.presentation.bucket.list;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.alexkn.syntact.R;
import com.alexkn.syntact.databinding.BucketListBucketCardBinding;
import com.alexkn.syntact.domain.model.views.BucketDetail;
import com.alexkn.syntact.presentation.common.ListItemAdapter;
import com.alexkn.syntact.presentation.common.ListItemViewHolder;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;

public class BucketAdapter extends ListItemAdapter<BucketDetail, BucketAdapter.BucketViewHolder> {

    @NonNull
    @Override
    public BucketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BucketListBucketCardBinding dataBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.bucket_list_bucket_card, parent, false);

        Drawable drawable = ResourcesCompat.getDrawable(parent.getResources(), R.drawable.fr, null);
        dataBinding.setFlag(drawable);

        return new BucketViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BucketViewHolder holder, int position) {

        BucketDetail bucket = getList().get(position);
        holder.bindTo(bucket);
    }

    static class BucketViewHolder extends ListItemViewHolder<BucketDetail> {

        final BucketListBucketCardBinding binding;

        BucketViewHolder(BucketListBucketCardBinding dataBinding) {

            super(dataBinding.getRoot());
            this.binding = dataBinding;
        }

        @Override
        public void bindTo(BucketDetail bucket) {

            Instant created = bucket.getCreatedAt();
            long days = DAYS.between(created, Instant.now()) + 1;
            double average = ((double) bucket.getSolvedCount()) / days;
            binding.setDailyAverage(Double.toString(average));

            binding.setBucket(bucket);
        }
    }
}
