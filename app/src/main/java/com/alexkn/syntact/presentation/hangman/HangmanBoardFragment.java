package com.alexkn.syntact.presentation.hangman;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexkn.syntact.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HangmanBoardFragment extends Fragment {

    private HangmanViewModel viewModel;

    private LetterListAdapter letterListAdapter2;

    private LetterListAdapter letterListAdapter1;

    private PhraseListAdapter phraseListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        this.setAllowEnterTransitionOverlap(true);
        this.setAllowReturnTransitionOverlap(true);

        View view = inflater.inflate(R.layout.hangman_board_fragment, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(HangmanViewModel.class);

        Long languagePairId = HangmanBoardFragmentArgs.fromBundle(getArguments())
                .getLanguagePairId();
        viewModel.setLanguagePairId(languagePairId);

        TextView scoreLabel = view.findViewById(R.id.boardLangScoreLabel);
        viewModel.getLanguagePair().observe(getViewLifecycleOwner(),
                languagePair -> scoreLabel.setText(String.valueOf(languagePair.getScore())));

        RecyclerView cardsView = view.findViewById(R.id.phrasesView);
        cardsView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new UnscrollableLinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        cardsView.setLayoutManager(linearLayoutManager);
        phraseListAdapter = new PhraseListAdapter(viewModel::solve);
        cardsView.setItemAnimator(new PhraseItemAnimator());
        phraseListAdapter
                .registerAdapterDataObserver(new PhraseAdapterDataObserver(linearLayoutManager));
        cardsView.setAdapter(phraseListAdapter);

        RecyclerView letterViewLeft = view.findViewById(R.id.lettersViewLeft);
        RecyclerView letterViewRight = view.findViewById(R.id.lettersViewRight);

        LinearLayoutManager linearLayoutManager1 = new UnscrollableLinearLayoutManager(
                getContext());
        linearLayoutManager1.setReverseLayout(true);
        letterViewLeft.setLayoutManager(linearLayoutManager1);
        letterListAdapter1 = new LetterListAdapter();
        letterViewLeft.setAdapter(letterListAdapter1);

        LinearLayoutManager linearLayoutManager2 = new UnscrollableLinearLayoutManager(
                getContext());
        linearLayoutManager2.setReverseLayout(true);
        letterViewRight.setLayoutManager(linearLayoutManager2);
        letterListAdapter2 = new LetterListAdapter();
        letterViewRight.setAdapter(letterListAdapter2);


        new Handler().postDelayed(() -> {
            viewModel.getLettersLeft().observe(getViewLifecycleOwner(), letterListAdapter1::submitList);
            viewModel.getLettersRight()
                    .observe(getViewLifecycleOwner(), letterListAdapter2::submitList);
            viewModel.getSolvablePhrases(languagePairId)
                    .observe(getViewLifecycleOwner(), phraseListAdapter::submitList);
        }, 300);

        return view;
    }

    private static class PhraseAdapterDataObserver extends RecyclerView.AdapterDataObserver {

        private final LinearLayoutManager linearLayoutManager;

        PhraseAdapterDataObserver(
                LinearLayoutManager linearLayoutManager) {this.linearLayoutManager = linearLayoutManager;}

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {

            linearLayoutManager.scrollToPosition(0);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {

            linearLayoutManager.scrollToPosition(0);
        }
    }

    private class UnscrollableLinearLayoutManager extends LinearLayoutManager {

        UnscrollableLinearLayoutManager(Context context) {

            super(context);
        }

        @Override
        public boolean canScrollVertically() {

            return false;
        }
    }
}
