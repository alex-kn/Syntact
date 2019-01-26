package com.alexkn.syntact.presentation.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class MainMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        View button = view.findViewById(R.id.startButton);
        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainMenuFragment_to_hangmanBoardFragment));
        return view;
    }
}
