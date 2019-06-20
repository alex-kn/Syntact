package com.alexkn.syntact.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window

import com.alexkn.syntact.R
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        //        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //        NavigationUI.setupWithNavController(bottomNav, navController);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    override fun onSupportNavigateUp(): Boolean {

        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    }
}
