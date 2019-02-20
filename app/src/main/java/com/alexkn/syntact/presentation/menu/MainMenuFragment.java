package com.alexkn.syntact.presentation.menu;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.LanguagePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeTransform;
import androidx.transition.Explode;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

public class MainMenuFragment extends Fragment {

    private View card;

    private LanguagesViewModel viewModel;


    private RecyclerView languagesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        View button = view.findViewById(R.id.startButton);

        viewModel = ViewModelProviders.of(getActivity()).get(LanguagesViewModel.class);


        this.setAllowEnterTransitionOverlap(true);
        this.setAllowReturnTransitionOverlap(true);

        languagesList = view.findViewById(R.id.languagesList);
        languagesList.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        languagesList.setAdapter(new LanguageAdapter(Arrays.asList("test"," 233")));
        languagesList.setHasFixedSize(true);

        button.setOnClickListener(this::startGame);
        return view;
    }

    public void startGame(View view) {
        Navigation.findNavController(view)
                .navigate(R.id.action_mainMenuFragment_to_hangmanBoardFragment);
    }

    public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

        private List<LanguagePair> languages = new ArrayList<>();

        private class LanguageViewHolder extends RecyclerView.ViewHolder {

            LanguageViewHolder(View v) {

                super(v);
            }
        }

        public LanguageAdapter() {

        }

        @Override
        public LanguageAdapter.LanguageViewHolder onCreateViewHolder(ViewGroup parent,
                int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.language_card, parent, false);
            return new LanguageViewHolder(v);
        }

        @Override
        public void onBindViewHolder(LanguageViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {

            return languages.size();
        }
    }
}
