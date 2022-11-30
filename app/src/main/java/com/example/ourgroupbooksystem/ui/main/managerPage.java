package com.example.ourgroupbooksystem.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ourgroupbooksystem.R;

public class managerPage extends AppCompatActivity {

    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_add);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        

    }

}
