package com.example.ourgroupbooksystem.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ourgroupbooksystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class login_main extends Activity {

    private TextView registerTv, loginTv;
    private EditText emailText, pwdText;
    private FirebaseAuth mAuth;
    Pattern emailPattern = android.util.Patterns.EMAIL_ADDRESS;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        registerTv = findViewById(R.id.registerTv);
                loginTv = findViewById(R.id.loginTv);

                mAuth = FirebaseAuth.getInstance();

                loginTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emailText = findViewById(R.id.email);
                        pwdText = findViewById(R.id.pwd);
                String emailTextString = emailText.getText().toString();
                String pwdTextString = pwdText.getText().toString();

                if(pwdTextString.length()<6) {
                    Toast.makeText(login_main.this, "비밀번호는 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if( !(emailPattern.matcher(emailTextString).matches()) ) {
                    Toast.makeText(login_main.this, "유효한 email을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginUser(emailTextString,pwdTextString);
            }
        });

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_main.this,register_main.class);
                startActivity(intent);
            }
        });
    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(login_main.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login_main.this, MainActivitys.class);
                            startActivity(intent);
                        } else {
                            // 로그인 실패
                            Toast.makeText(login_main.this, "이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
