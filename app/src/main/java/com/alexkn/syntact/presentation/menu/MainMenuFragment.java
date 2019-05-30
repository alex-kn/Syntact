package com.alexkn.syntact.presentation.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alexkn.syntact.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainMenuFragment extends Fragment {

    private LanguagesViewModel viewModel;

    private RecyclerView languagesList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        FloatingActionButton button = view.findViewById(R.id.newBucketFab);

        FloatingActionButton tempButton = view.findViewById(R.id.tempTemplateNavFab);

        tempButton.setOnClickListener(v -> {

            NavDirections navDirections = MainMenuFragmentDirections
                    .actionMainMenuFragmentToTemplateFragment();
            Navigation.findNavController(view).navigate(navDirections);
        });

        viewModel = ViewModelProviders.of(getActivity()).get(LanguagesViewModel.class);

        this.setAllowEnterTransitionOverlap(true);
        this.setAllowReturnTransitionOverlap(true);

        languagesList = view.findViewById(R.id.languagesList);
        languagesList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        LanguageAdapter languageAdapter = new LanguageAdapter(
                bucket -> viewModel.deleteLanguage(bucket));
        languagesList.setAdapter(languageAdapter);
        languagesList.setHasFixedSize(true);

        viewModel.getBuckets().observe(getViewLifecycleOwner(), languageAdapter::submitList);
        button.setOnClickListener(this::newLanguage);

        return view;
    }

    private void newLanguage(View view) {

        NavDirections navDirections = MainMenuFragmentDirections
                .actionMainMenuFragmentToAddLanguageFragment();
        Navigation.findNavController(view).navigate(navDirections);
    }
}
