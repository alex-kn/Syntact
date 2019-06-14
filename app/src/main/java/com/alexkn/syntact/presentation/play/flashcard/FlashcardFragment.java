package com.alexkn.syntact.presentation.play.flashcard;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;
import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.databinding.FlashcardFragmentBinding;
import com.alexkn.syntact.presentation.play.board.BoardFragmentArgs;

public class FlashcardFragment extends Fragment {

    private FlashcardViewModel viewModel;

    public static FlashcardFragment newInstance() {

        return new FlashcardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FlashcardFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.flashcard_fragment, container, false);
        viewModel = ViewModelProviders
                .of(this, ((ApplicationComponentProvider) getActivity().getApplication()).getApplicationComponent().flashcardViewModelFactory())
                .get(FlashcardViewModel.class);

        Long bucketId = FlashcardFragmentArgs.fromBundle(getArguments()).getBucketId();
        viewModel.init(bucketId);

        viewModel.getTranslations().observe(getViewLifecycleOwner(), translations -> {
            binding.setClue(translations.get(0).getClue().getText());
            binding.setSolution(translations.get(0).getSolvableItem().getText());
        });
        return binding.getRoot();
    }
}
