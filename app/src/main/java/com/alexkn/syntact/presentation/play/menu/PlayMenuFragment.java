package com.alexkn.syntact.presentation.play.menu;

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
import com.alexkn.syntact.presentation.play.board.BoardViewModel;
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

        languagesList = view.findViewById(R.id.languagesList);
        languagesList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        PlayableBucketAdapter playableBucketAdapter = new PlayableBucketAdapter(
                bucket -> viewModel.deleteLanguage(bucket));
        languagesList.setAdapter(playableBucketAdapter);
        languagesList.setHasFixedSize(true);

        viewModel.getBuckets().observe(getViewLifecycleOwner(), playableBucketAdapter::submitList);

        return view;
    }
}
