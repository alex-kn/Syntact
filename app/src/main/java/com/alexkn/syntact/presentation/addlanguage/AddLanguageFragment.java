package com.alexkn.syntact.presentation.addlanguage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.alexkn.syntact.R;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddLanguageFragment extends Fragment {

    private static final String TAG = AddLanguageFragment.class.getSimpleName();

    private AddLanguageViewModel viewModel;

    private View addButton;

    private Locale selectedSourceLanguage;

    private Locale selectedTargetLanguage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_language_fragment, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(AddLanguageViewModel.class);

        addButton = view.findViewById(R.id.addBucketButton);

        List<Locale> languages = viewModel.getAvailableBuckets();
        List<List<Locale>> dataset = Arrays.asList(languages, languages);
        selectedTargetLanguage = languages.get(0);
        selectedSourceLanguage = languages.get(0);

        RecyclerView recyclerView = view.findViewById(R.id.bucketOptionRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new BucketOptionsAdapter(dataset, (integer, locale) -> {
            if (integer == 0) {
                selectedSourceLanguage = locale;
            } else if (integer == 1) {
                selectedTargetLanguage = locale;
            }
        }));

        addButton.setOnClickListener(v -> {

            viewModel.addLanguage(selectedSourceLanguage, selectedTargetLanguage);
            Navigation.findNavController(view).popBackStack();
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AddLanguageViewModel.class);
    }
}
