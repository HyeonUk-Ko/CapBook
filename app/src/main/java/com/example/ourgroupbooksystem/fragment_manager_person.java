package com.example.ourgroupbooksystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourgroupbooksystem.ui.main.login_main;
import com.google.firebase.auth.FirebaseAuth;

public class fragment_manager_person extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personfragment, container, false);

        TextView emailInfo = v.findViewById(R.id.emailInfo);
        Button logoutBtn = v.findViewById(R.id.logout);
        Button pwdChangeBtn = v.findViewById(R.id.pwdChange);


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("data", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();;
        String email = sharedPreferences.getString("email","error");

        emailInfo.setText(email);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), login_main.class);
                startActivity(intent);
            }
        });

        pwdChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.sendPasswordResetEmail(email);
                Toast.makeText(getActivity(), "비밀번호 변경 확인 이메일을 보냈습니다.", Toast.LENGTH_SHORT).show();

                editor.clear();
                editor.commit();
            }
        });


        return v;
    }
}