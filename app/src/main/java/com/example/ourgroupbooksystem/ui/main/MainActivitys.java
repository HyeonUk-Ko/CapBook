package com.example.ourgroupbooksystem.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ourgroupbooksystem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivitys extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        bottomNavigationView =findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new homefragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new homefragment()).commit();
                        break;
                    case R.id.nav_favorite:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new favoritefragment()).commit();
                        break;
                    case R.id.nav_my:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new personfragment()).commit();
                        break;

                }
                return true;
            }
        });

    }

}