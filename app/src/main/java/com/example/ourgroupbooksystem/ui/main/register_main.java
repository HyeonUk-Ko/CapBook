package com.example.ourgroupbooksystem.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ourgroupbooksystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class register_main extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    private Button sign_up, cancelBtn;
    private long backKeyPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();

        cancelBtn = findViewById(R.id.cancel_button);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register_main.this,login_main.class);
                startActivity(intent);
            }
        });

        sign_up = findViewById(R.id.signUpButton);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    // When initializing your Activity, check to see if the user is currently signed in.
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    private void signUp() {
        // ?????????
        EditText emailEditText = findViewById(R.id.emailEditText);
        String email = emailEditText.getText().toString();

        Pattern emailPattern = android.util.Patterns.EMAIL_ADDRESS;

        // ????????????
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText passwordCheckText = findViewById(R.id.passwordCheckEditText);
        String password = passwordEditText.getText().toString();
        String passwordCheck = passwordCheckText.getText().toString();

        if(!(emailPattern.matcher(email).matches())){
            Toast.makeText(register_main.this, "????????? ???????????? ??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length()<6){
            Toast.makeText(register_main.this, "??????????????? 6??? ?????? ??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(password.equals(passwordCheck))){
            Toast.makeText(register_main.this, "????????? ??????????????? ???????????? ????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(register_main.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(register_main.this, login_main.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(register_main.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                });
    }

    Intent intent = getIntent();

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'??????\' ????????? ?????? ??? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            moveTaskToBack(true);
            finishAndRemoveTask();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}
