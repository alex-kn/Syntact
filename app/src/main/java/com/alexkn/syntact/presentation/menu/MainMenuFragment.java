package com.alexkn.syntact.presentation.menu;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkn.syntact.R;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

public class MainMenuFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final ChangeBounds transition = new ChangeBounds();
        transition.setDuration(600L);
        TransitionManager.beginDelayedTransition(container, transition);

        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        View button = view.findViewById(R.id.startButton);
        //        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainMenuFragment_to_hangmanBoardFragment));

        button.setOnClickListener(this::startGame);
        return view;
    }

    public void startGame(View view){

        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder().addSharedElement(getView().findViewById(R.id.topCard), "card_view").build();

        Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_hangmanBoardFragment, null, null, extras);


    }
}
