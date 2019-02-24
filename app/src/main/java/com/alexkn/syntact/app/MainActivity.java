package com.alexkn.syntact.app;

import android.os.Bundle;
import android.view.View;

import com.alexkn.syntact.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


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
