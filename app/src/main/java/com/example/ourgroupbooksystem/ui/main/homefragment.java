package com.example.ourgroupbooksystem.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.ourgroupbooksystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class homefragment extends Fragment {

    private DatabaseReference mDatabase;
    private List<BookDataVO> result = new LinkedList<BookDataVO>();
    ArrayList<ListData> listViewData = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_homefragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView= view.findViewById(R.id.list);

        mDatabase = FirebaseDatabase.getInstance().getReference();
//        BookDataVO bookVO = new BookDataVO("1","1","1","1","1");
//
//        writeData(bookVO);

//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
////                List<BookDataVO> tmpList = new LinkedList<BookDataVO>();
////                tmpList.add(dataSnapshot.getValue.BookDataVO.class);
//                BookDataVO post = dataSnapshot.getValue(BookDataVO.class);
//                // ..
//                System.err.println("post : "+post);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w("loadPost:onCancelled", databaseError.toException());
//            }
//        };
//        mPostReference.addValueEventListener(postListener);

        mDatabase.child("bookList").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                Log.d("firebase", String.valueOf(task.getResult().getValue()));

                for (DataSnapshot ds : task.getResult().getChildren()) {
                    BookDataVO tmp = ds.getValue(BookDataVO.class);
                    result.add(tmp);
                }

                ListAdapter aAdapter = new CustomListView(initListData(result));
                listView.setAdapter(aAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String clickName = listViewData.get(position).title;
                        Log.d("확인", "name : " + clickName);
                    }
                });
            }
        });
    }

    public void writeData(BookDataVO data) {
        mDatabase.child("bookList").child("3").setValue(data);
    }

    public ArrayList<ListData> initListData(List<BookDataVO> result) {
        System.err.println(result);
        for (BookDataVO tmp : result) {
            ListData listData = new ListData();

            listData.mainImage = R.drawable.ic_launcher_foreground;
            listData.star = R.drawable.ic_launcher_foreground;

            listData.title = tmp.getBookName();
            listData.body_1 =  tmp.getPublisher() + " / " + tmp.getAuthor();
            listData.body_2 = tmp.getPublishedDate() + " / " + tmp.getIsbn();

            listViewData.add(listData);
        }
        return listViewData;
    }
}