package com.example.ourgroupbooksystem.ui.main;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class fragment_manager_book_add extends Fragment {

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {// xml 레이아웃 파일과 연결

        return inflater.inflate(R.layout.fragment_manager_book_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        EditText bookName = (EditText) view.findViewById(R.id.bookNameForBookAdd);
        EditText author = (EditText) view.findViewById(R.id.authorForBookAdd);
        EditText publishedDate = (EditText) view.findViewById(R.id.publishedDateForBookAdd);
        EditText publisher = (EditText) view.findViewById(R.id.publisherForBookAdd);
        EditText price = (EditText) view.findViewById(R.id.priceForBookAdd);
        EditText quantity = (EditText) view.findViewById(R.id.quantityForBookAdd);
        EditText isbn = (EditText) view.findViewById(R.id.isbnForBookAdd);

        Button addBookBtn = (Button) view.findViewById(R.id.addBookBtn);

        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bookNameData = bookName.getText().toString();
                String authorData = author.getText().toString();
                String publishedDateData = publishedDate.getText().toString();
                String publisherData = publisher.getText().toString();
                String isbnData = isbn.getText().toString();

                // validation for int
                if(price.getText().toString().length()>0 || quantity.getText().toString().length()>0) {
                    Integer priceData = Integer.parseInt(price.getText().toString());
                    Integer quantityData = Integer.parseInt(quantity.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "빈칸 없이 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // validation for String
                if(bookNameData.equals("") || authorData.equals("") || publishedDateData.equals("") ||
                    publisherData.equals("") || isbnData.equals("")){
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
                tmp.setQuantitiy(Integer.parseInt(quantity.getText().toString()));

                addBookData(tmp);
                bookName.setText("");
                author.setText("");
                publishedDate.setText("");
                publisher.setText("");
                isbn.setText("");
                price.setText(null);
                quantity.setText(null);
            }
        });

    }

    public void addBookData(BookDataVO data) {
        mDatabase.child("bookList").child(data.isbn).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "등록 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }
}