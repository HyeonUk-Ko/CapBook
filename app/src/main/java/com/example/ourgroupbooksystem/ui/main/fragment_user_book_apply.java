package com.example.ourgroupbooksystem.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ourgroupbooksystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class fragment_user_book_apply extends Fragment {

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_book_apply, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        EditText bookName = (EditText) view.findViewById(R.id.bookNameForBookApply);
        EditText author = (EditText) view.findViewById(R.id.authorForBookApply);
        EditText publishedDate = (EditText) view.findViewById(R.id.publishedDateForBookApply);
        EditText publisher = (EditText) view.findViewById(R.id.publisherForBookApply);
        EditText isbn = (EditText) view.findViewById(R.id.isbnForBookApply);
        EditText applyBookQuantitiy = (EditText) view.findViewById(R.id.applyBookQuantity);
        EditText price = (EditText) view.findViewById(R.id.priceForBookApply);

        Button applyBookBtn = (Button) view.findViewById(R.id.submitApplyBtn);

        applyBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookNameData = bookName.getText().toString();
                String authorData = author.getText().toString();
                String publishedDateData = publishedDate.getText().toString();
                String publisherData = publisher.getText().toString();
                String isbnData = isbn.getText().toString();

                // validation for int
                if(price.getText().toString().length()>0 || applyBookQuantitiy.getText().toString().length()>0) {
                    Integer priceData = Integer.parseInt(price.getText().toString());
                    Integer applyBookQuantitiyData = Integer.parseInt(applyBookQuantitiy.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "빈칸 없이 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // validation for String
                if (bookNameData.equals("") || authorData.equals("") || publishedDateData.equals("") ||
                    publisherData.equals("") || isbnData.equals("")) {
                    Toast.makeText(getActivity(), "빈칸 없이 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }


                BookDataVO tmp = new BookDataVO();
                tmp.setBookName(bookName.getText().toString());
                tmp.setAuthor(author.getText().toString());
                tmp.setPublishedDate(publishedDate.getText().toString());
                tmp.setPublisher(publisher.getText().toString());
                tmp.setIsbn(isbn.getText().toString());
                tmp.setPrice(Integer.parseInt(price.getText().toString()));
                tmp.setQuantitiy(Integer.parseInt(applyBookQuantitiy.getText().toString()));

                applyBook(tmp);
                bookName.setText("");
                author.setText("");
                publishedDate.setText("");
                publisher.setText("");
                isbn.setText("");
                price.setText(null);
                applyBookQuantitiy.setText(null);
            }
        });
    }
    public void applyBook(BookDataVO data) {
        mDatabase.child("applyBook").child(data.getIsbn()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "도서 신청 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }
}