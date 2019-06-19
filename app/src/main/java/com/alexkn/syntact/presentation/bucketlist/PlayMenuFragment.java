package com.alexkn.syntact.presentation.bucketlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PlayMenuFragment extends Fragment {

    private PlayMenuViewModel viewModel;

    private RecyclerView languagesList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.play_menu_fragment, container, false);

        viewModel =ViewModelProviders.of(this, ((ApplicationComponentProvider) getActivity().getApplication()).getApplicationComponent().playMenuViewModelFactory())
                .get(PlayMenuViewModel.class);

        FloatingActionButton fab = view.findViewById(R.id.createBucketFab);
        fab.setOnClickListener(this::newBucket);


        languagesList = view.findViewById(R.id.languagesList);
        languagesList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        BucketAdapter bucketAdapter = new BucketAdapter();
        languagesList.setAdapter(bucketAdapter);
        languagesList.setHasFixedSize(true);

        viewModel.getBuckets().observe(getViewLifecycleOwner(), bucketAdapter::submitList);

        return view;
    }

    private void newBucket(View view) {

        NavDirections action = PlayMenuFragmentDirections.actionPlayMenuFragmentToCreateBucketFragment();
        Navigation.findNavController(view).navigate(action);
    }
}
