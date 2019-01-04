package com.alexkn.syntact.hangwords.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexkn.syntact.R;
import com.alexkn.syntact.hangwords.ui.util.Letter;

import java.util.List;

public class HangwordsFragment extends Fragment {

    private HangwordsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hangwords_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(HangwordsViewModel.class);

        RecyclerView cardsView = view.findViewById(R.id.phrasesView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        cardsView.setLayoutManager(linearLayoutManager);
        PhraseListAdapter phraseListAdapter = new PhraseListAdapter(
                (solvablePhrase, letterId) -> viewModel.solve(solvablePhrase, letterId));
        phraseListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                linearLayoutManager.scrollToPosition(0);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                linearLayoutManager.scrollToPosition(0);
            }
        });
        cardsView.setAdapter(phraseListAdapter);

        createLetterRecyclerView(view.findViewById(R.id.lettersViewLeft), viewModel.getLetters1());
        createLetterRecyclerView(view.findViewById(R.id.lettersViewCenter),
                viewModel.getLetters2());
        createLetterRecyclerView(view.findViewById(R.id.lettersViewRight), viewModel.getLetters3());

        viewModel.getSolvablePhrases().observe(this, phraseListAdapter::submitList);

        return view;
    }

    private void createLetterRecyclerView(RecyclerView recyclerView,
            LiveData<List<Letter>> liveData) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        LetterListAdapter letterListAdapter = new LetterListAdapter();
        recyclerView.setAdapter(letterListAdapter);

        liveData.observe(this, letterListAdapter::submitList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
