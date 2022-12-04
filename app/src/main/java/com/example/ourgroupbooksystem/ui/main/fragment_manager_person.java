package com.example.ourgroupbooksystem.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourgroupbooksystem.R;
import com.example.ourgroupbooksystem.ui.main.login_main;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class fragment_manager_person extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Dialog codeChangeDialog;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        codeChangeDialog = new Dialog(getActivity());       // Dialog 초기화
        codeChangeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        codeChangeDialog.setContentView(R.layout.dialog_manager_code_change);

        View v = inflater.inflate(R.layout.fragment_manager_person, container, false);

        Button logoutBtn = v.findViewById(R.id.logout2);
        Button codeChangeBtn = v.findViewById(R.id.codeChangeBtn);
        Button applyBookBoardBtn = v.findViewById(R.id.bookApplicationBoardBtn);


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

        codeChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                codeChangeDialog.show();
                Window window = codeChangeDialog.getWindow();

                Button codeSubmit = codeChangeDialog.findViewById(R.id.codeChangeSubmit);
                Button dialogClose = codeChangeDialog.findViewById(R.id.closeDialog2);
                EditText beforeCode = codeChangeDialog.findViewById(R.id.beforeCode);
                EditText afterCode = codeChangeDialog.findViewById(R.id.afterCode);

                codeSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String beforeCodeData = beforeCode.getText().toString();
                        String afterCodeData = afterCode.getText().toString();

                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("/");

                        DatabaseReference codeRef = ref.child("managerCode");

                        codeRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                String managerLoginCode = task.getResult().getValue().toString();
                                if(beforeCodeData.equals(managerLoginCode)){
                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference codeRef = database.getReference("/managerCode");
                                    codeRef.setValue(afterCodeData);
                                    Toast.makeText(getContext(), "관리자코드 변경 성공", Toast.LENGTH_SHORT).show();
                                    beforeCode.setText("");
                                    afterCode.setText("");
                                    codeChangeDialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "관리자코드 변경 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                dialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        codeChangeDialog.dismiss();
                    }
                });
            }
        });

        applyBookBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), activity_applied_book_list.class);
                startActivity(intent);
            }
        });

        return v;
    }
}