package com.alexkn.syntact.presentation.mainmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.SyntactApplication;
import com.alexkn.syntact.domain.SyntactComponent;
import com.alexkn.syntact.domain.usecase.PhraseUseCase;
import com.alexkn.syntact.presentation.hangman.main.HangmanActivity;

import javax.inject.Inject;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {

        Intent intent = new Intent(this, HangmanActivity.class);
        startActivity(intent);
    }
}
