package com.example.ourgroupbooksystem.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ourgroupbooksystem.R;

public class login_main extends Activity {

    private TextView registerTv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        registerTv = findViewById(R.id.registerTv);


        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_main.this,register_main.class);
                startActivity(intent);
            }
        });
    }
}
