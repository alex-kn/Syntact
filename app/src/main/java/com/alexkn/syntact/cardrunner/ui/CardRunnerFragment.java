package com.alexkn.syntact.cardrunner.ui;

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


public class CardRunnerFragment extends Fragment {

    private CardRunnerViewModel viewModel;

    private RecyclerView cardsView;
    private RecyclerView.Adapter cardAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_runner_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(CardRunnerViewModel.class);

        cardsView = view.findViewById(R.id.cardsView);
        cardsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CardAdapter adapter = new CardAdapter();
        cardsView.setAdapter(adapter);

        viewModel.getPhrases().observe(this, phrases -> adapter.setData(phrases));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

}
