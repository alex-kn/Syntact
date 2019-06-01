package com.alexkn.syntact.presentation.play.board;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.alexkn.syntact.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BoardFragment extends Fragment {

    private BoardViewModel viewModel;

    private LetterListAdapter letterListAdapter2;

    private LetterListAdapter letterListAdapter1;

    private PhraseListAdapter phraseListAdapter;

    private RecyclerView letterViewLeft;

    private RecyclerView letterViewRight;

    private ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.board_fragment, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(BoardViewModel.class);

        Long bucketId = BoardFragmentArgs.fromBundle(getArguments()).getBucketId();
        viewModel.initLanguage(bucketId);

        RecyclerView cardsView = view.findViewById(R.id.phrasesView);
        cardsView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new UnscrollableLinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        cardsView.setLayoutManager(linearLayoutManager);
        phraseListAdapter = new PhraseListAdapter(viewModel::solve);
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
        reloadButton.setOnClickListener(v -> AsyncTask.execute(viewModel::reloadLetters));

        return view;
    }

    private void updateProgressBar(int current, int max) {

        progress.setProgress(current / max * 100);
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
