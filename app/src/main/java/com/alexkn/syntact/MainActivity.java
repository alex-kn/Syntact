package com.alexkn.syntact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alexkn.syntact.crosswordpuzzle.CrosswordPuzzleActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, CrosswordPuzzleActivity.class);
        startActivity(intent);
    }
}
