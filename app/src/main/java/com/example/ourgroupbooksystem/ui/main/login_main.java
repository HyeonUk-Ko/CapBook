package com.example.ourgroupbooksystem.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ourgroupbooksystem.ManagerActivity;
import com.example.ourgroupbooksystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class login_main extends Activity {

    private Button registerTv, loginTv, managerLoginBtn;
    private EditText emailText, pwdText;
    private FirebaseAuth mAuth;
    private CheckBox autoLoginCheckBox;
    Pattern emailPattern = android.util.Patterns.EMAIL_ADDRESS;
    Dialog managerLoginDialog;

    private long backKeyPressedTime = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        registerTv = findViewById(R.id.registerTv);
        loginTv = findViewById(R.id.loginTv);
        autoLoginCheckBox = findViewById(R.id.autoLogin);
        managerLoginBtn = findViewById(R.id.managerLogin);

        //dialog
        managerLoginDialog = new Dialog(login_main.this);       // Dialog 초기화
        managerLoginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        managerLoginDialog.setContentView(R.layout.dialog_manager_login);

        mAuth = FirebaseAuth.getInstance();
        autoLogin();

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("data", 0);
                editor = sharedPreferences.edit();
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
                if(autoLoginCheckBox.isChecked()) {
                    editor.putBoolean("autoLogin",true);
                    editor.commit();
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

        managerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button codeSubmit = managerLoginDialog.findViewById(R.id.codeSubmit);
                Button dialogClose = managerLoginDialog.findViewById(R.id.closeDialog);
                EditText codeInputArea = managerLoginDialog.findViewById(R.id.codeInput);

                managerLoginDialog.show();
                Window window = managerLoginDialog.getWindow();
                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                codeSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String code = codeInputArea.getText().toString();
                        managerLogin(code);
                    }
                });

                dialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        managerLoginDialog.dismiss();
                    }
                });
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
                            sharedPreferences = getSharedPreferences("data", 0);
                            boolean checkAutoLoginHistory = sharedPreferences.getBoolean("autoLogin",false);
                            if(checkAutoLoginHistory) {
                                editor = sharedPreferences.edit();
                                editor.putString("email",email);
                                editor.putString("password",password);
                                editor.commit();
                                Toast.makeText(login_main.this, "자동 로그인 성공", Toast.LENGTH_SHORT).show();
                            } else {
                                editor = sharedPreferences.edit();
                                editor.putString("email",email);
                                editor.commit();
                                Toast.makeText(login_main.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(login_main.this, MainActivitys.class);
                            startActivity(intent);
                        } else {
                            // 로그인 실패
                            Toast.makeText(login_main.this, "이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void autoLogin() {
        sharedPreferences  = getSharedPreferences("data", 0);

        boolean checkAutoLoginHistory = sharedPreferences.getBoolean("autoLogin",false);
        if(checkAutoLoginHistory) {
            String email = sharedPreferences.getString("email","!!emailAutoLoginError!!");
            String password = sharedPreferences.getString("password","!!passwordAutoLoginError!!");
            if(email.equals("!!emailAutoLoginError!!")){
                Toast.makeText(login_main.this, "[자동로그인 오류] 다시 로그인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            if(password.equals("!!passwordAutoLoginError!!")){
                Toast.makeText(login_main.this, "[자동로그인 오류] 다시 로그인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email,password);
        }
    }

    public void managerLogin(String code) {
        String managerLoginCode = getResources().getString(R.string.manger_login_code);
        System.err.println("managerLoginCode=====>"+managerLoginCode);
        if(code.equals(managerLoginCode)){
            managerLoginDialog.dismiss();
            Toast.makeText(login_main.this, "관리자 로그인 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(login_main.this, managerPage.class);
            startActivity(intent);
        } else {
            managerLoginDialog.dismiss();
            Toast.makeText(login_main.this, "관리자 코드 불일치", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            moveTaskToBack(true);
            finishAndRemoveTask();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
