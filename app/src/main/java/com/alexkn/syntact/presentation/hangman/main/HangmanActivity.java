package com.alexkn.syntact.presentation.hangman.main;

import android.os.Bundle;

import com.alexkn.syntact.R;

import androidx.fragment.app.FragmentActivity;

public class HangmanActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangman_activity);
    }
}
