package com.alexkn.syntact.presentation.app;

import android.os.Bundle;
import android.view.View;

import com.alexkn.syntact.R;

import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {

//        Intent intent = new Intent(this, HangmanActivity.class);
//        startActivity(intent);
    }
}
