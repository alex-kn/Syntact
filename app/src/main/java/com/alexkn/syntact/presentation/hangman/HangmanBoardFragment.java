package com.alexkn.syntact.presentation.hangman;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ProgressBar;
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

    private RecyclerView letterViewLeft;

    private RecyclerView letterViewRight;

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
        viewModel.initLanguage(languagePairId);

        TextView scoreLabel = view.findViewById(R.id.boardLangScoreLabel);
        viewModel.getLanguagePair().observe(getViewLifecycleOwner(),
                languagePair -> scoreLabel.setText(String.valueOf(languagePair.getScore())));

        TextView maxScoreLabel = view.findViewById(R.id.boardMaxScoreLabel);
        viewModel.getMaxScore().observe(getViewLifecycleOwner(),
                score -> maxScoreLabel.setText(String.valueOf(score)));
        
        TextView levelLabel = view.findViewById(R.id.boardLevelValue);
        viewModel.getLanguagePair().observe(getViewLifecycleOwner(),
                lp -> levelLabel.setText(String.valueOf(lp.getLevel())));

        view.findViewById(R.id.boardLevelValue);

        TextView streakTextView = view.findViewById(R.id.boardStreakValue);
        viewModel.getLanguagePair().observe(getViewLifecycleOwner(),
                languagePair -> streakTextView.setText(String.valueOf(languagePair.getStreak())));

        ProgressBar progress = view.findViewById(R.id.boardLangProgressBar);
        viewModel.getLanguagePair().observe(getViewLifecycleOwner(),
                languagePair -> progress.setProgress(languagePair.getScore(), true));
        viewModel.getMaxScore().observe(getViewLifecycleOwner(), score -> progress.setMax(score));

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

        letterViewLeft = view.findViewById(R.id.lettersViewLeft);
        letterViewRight = view.findViewById(R.id.lettersViewRight);

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

        viewModel.getLettersLeft().observe(getViewLifecycleOwner(), letterListAdapter1::submitList);
        viewModel.getLettersRight()
                .observe(getViewLifecycleOwner(), letterListAdapter2::submitList);
        viewModel.getSolvablePhrases()
                .observe(getViewLifecycleOwner(), phraseListAdapter::submitList);

        Button reloadButton = view.findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(v -> new ReloadLettersTask().execute());

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

    private class ReloadLettersTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            viewModel.reloadLetters();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            letterViewLeft.scheduleLayoutAnimation();
            letterViewRight.scheduleLayoutAnimation();
        }
    }
}
