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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alexkn.syntact.R;
import com.alexkn.syntact.hangwords.logic.LetterManagement;

import java.util.List;

public class HangwordsFragment extends Fragment {

    private HangwordsViewModel viewModel;

    private RecyclerView cardsView;
    private RecyclerView lettersView1;
    private RecyclerView lettersView2;
    private RecyclerView lettersView3;

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
        phraseListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                linearLayoutManager.scrollToPosition(0);
            }
        });
        cardsView.setAdapter(phraseListAdapter);

        lettersView1 = createLetterRecyclerView(view.findViewById(R.id.lettersViewLeft),
                viewModel.getLetters1());
        lettersView2 = createLetterRecyclerView(view.findViewById(R.id.lettersViewCenter),
                viewModel.getLetters2());
        lettersView3 = createLetterRecyclerView(view.findViewById(R.id.lettersViewRight),
                viewModel.getLetters3());

        viewModel.getSolvablePhrases().observe(this, phraseListAdapter::submitList);

        return view;
    }

    private RecyclerView createLetterRecyclerView(RecyclerView recyclerView,
            LiveData<List<Letter>> liveData) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()){};
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        LetterListAdapter letterListAdapter = new LetterListAdapter(){};
        recyclerView.setAdapter(letterListAdapter);

        liveData.observe(this, letterListAdapter::submitList);
        return recyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
