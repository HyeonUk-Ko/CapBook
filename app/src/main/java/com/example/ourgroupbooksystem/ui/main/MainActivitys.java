package com.example.ourgroupbooksystem.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ourgroupbooksystem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivitys extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private homefragment frag1;
    private favoritefragment frag2;
    private personfragment frag3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        setFrag(0);
                        break;
                    case R.id.nav_favorite:
                        setFrag(1);
                        break;
                    case R.id.nav_my:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });
        frag1 = new homefragment();
        frag2 = new favoritefragment();
        frag3 = new personfragment();
        setFrag(0);

    }

    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.frameLayout,frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.frameLayout,frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.frameLayout,frag3);
                ft.commit();
                break;
        }
    }

}
