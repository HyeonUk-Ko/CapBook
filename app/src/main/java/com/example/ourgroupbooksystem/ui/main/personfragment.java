package com.example.ourgroupbooksystem.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ourgroupbooksystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;


public class personfragment extends Fragment {


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personfragment, container, false);

        Button logoutBtn = v.findViewById(R.id.logout);
        Button pwdChaneBtn = v.findViewById(R.id.pwdChange);
        TextView loginInfo = v.findViewById(R.id.loginInfo);

        String userEmail = mAuth.getCurrentUser().getEmail();

        loginInfo.setText(userEmail);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("data", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();;

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

        pwdChaneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.sendPasswordResetEmail(userEmail);
                Toast.makeText(getActivity(), "비밀번호 변경 확인 이메일을 보냈습니다.", Toast.LENGTH_SHORT).show();

                editor.clear();
                editor.commit();
            }
        });

        return v;
    }
}