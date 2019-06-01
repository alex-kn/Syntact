package com.alexkn.syntact.presentation.bucket.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexkn.syntact.R;

public class ListBucketsFragment extends Fragment {

    private ListBucketsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(ListBucketsViewModel.class);

        View view = inflater.inflate(R.layout.bucket_list_fragment, container, false);

        RecyclerView list = view.findViewById(R.id.bucketList);
        BucketAdapter adapter = new BucketAdapter();
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getBuckets().observe(getViewLifecycleOwner(), data -> {
            adapter.submitList(data);
        });
        return view;
    }
}
