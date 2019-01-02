package com.alexkn.syntact.hangwords.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexkn.syntact.R;


public class HangwordsFragment extends Fragment  {

    private HangwordsViewModel viewModel;

    private RecyclerView cardsView;
    private RecyclerView lettersView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hangwords_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(HangwordsViewModel.class);

        cardsView = view.findViewById(R.id.phrasesView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        cardsView.setLayoutManager(linearLayoutManager);
        PhraseListAdapter phraseListAdapter = new PhraseListAdapter();
        cardsView.setAdapter(phraseListAdapter);

        lettersView = view.findViewById(R.id.lettersView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setReverseLayout(true);
        lettersView.setLayoutManager(gridLayoutManager);
        LetterListAdapter letterListAdapter = new LetterListAdapter();
        lettersView.setAdapter(letterListAdapter);

        viewModel.getLetters().observe(this, letterListAdapter::submitList);
        viewModel.getPhrases().observe(this, phraseListAdapter::submitList);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
