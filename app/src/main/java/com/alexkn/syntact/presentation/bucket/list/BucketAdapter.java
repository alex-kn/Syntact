package com.alexkn.syntact.presentation.bucket.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alexkn.syntact.R;
import com.alexkn.syntact.databinding.BucketListBucketCardBinding;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.presentation.common.ListItemAdapter;
import com.alexkn.syntact.presentation.common.ListItemViewHolder;

public class BucketAdapter extends ListItemAdapter<Bucket, BucketAdapter.BucketViewHolder> {

    @NonNull
    @Override
    public BucketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BucketListBucketCardBinding dataBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.bucket_list_bucket_card,
                        parent, false);

        return new BucketViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BucketViewHolder holder, int position) {

        Bucket bucket = getList().get(position);
        holder.bindTo(bucket);
    }

    static class BucketViewHolder extends ListItemViewHolder<Bucket> {

        private final BucketListBucketCardBinding binding;

        public BucketViewHolder(BucketListBucketCardBinding dataBinding) {

            super(dataBinding.getRoot());
            this.binding = dataBinding;
        }

        @Override
        public void bindTo(Bucket entity) {

            binding.setLanguage(entity.getLanguage().getDisplayLanguage());
        }
    }
}
