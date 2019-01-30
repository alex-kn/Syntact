package com.alexkn.syntact.presentation.hangman.board;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.alexkn.syntact.presentation.hangman.common.HangmanViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeTransform;
import androidx.transition.Explode;
import androidx.transition.TransitionManager;

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

        View view = inflater.inflate(R.layout.fragment_hangman_board, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(HangmanViewModel.class);

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

        viewModel.getSolvablePhrases().observe(this, phraseListAdapter::submitList);
        viewModel.getLettersLeft().observe(this, letterListAdapter1::submitList);
        viewModel.getLettersRight().observe(this, letterListAdapter2::submitList);

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
