package com.example.ourgroupbooksystem.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ourgroupbooksystem.R;

public class register_main extends Activity {

    TextView okTv,cancelTv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        cancelTv = findViewById(R.id.cancelTv);

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register_main.this,login_main.class);
                startActivity(intent);
            }
        });


    }
}
