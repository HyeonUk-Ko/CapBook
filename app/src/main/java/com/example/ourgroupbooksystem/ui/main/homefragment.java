package com.example.ourgroupbooksystem.ui.main;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    ListView listView;
    Dialog dilaog01;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dilaog01 = new Dialog(getContext());       // Dialog 초기화
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog01.setContentView(R.layout.dialog_book);             // xml 레이아웃 파일과 연결

        return inflater.inflate(R.layout.fragment_homefragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView= view.findViewById(R.id.list);
        Button searchBtn = (Button) view.findViewById(R.id.searchBtn);
        EditText searchBox = (EditText) view.findViewById(R.id.searchBox);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("bookList").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                Log.d("firebase", String.valueOf(task.getResult().getValue()));

                result.clear();

                for (DataSnapshot ds : task.getResult().getChildren()) {
                    BookDataVO tmp = ds.getValue(BookDataVO.class);
                    result.add(tmp);
                }

                ListAdapter aAdapter = new CustomListView(initListData(result));
                listView.setAdapter(aAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dilaog01.show();
                        Window window = dilaog01.getWindow();
                        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        Button close_btn=dilaog01.findViewById(R.id.btn_close);
                        TextView name = dilaog01.findViewById(R.id.book_name);
                        TextView inform = dilaog01.findViewById(R.id.inform);
                        TextView dateInform = dilaog01.findViewById(R.id.date_inform);
                        TextView publisher = dilaog01.findViewById(R.id.made_inform);
                        TextView price = dilaog01.findViewById(R.id.price_inform);
                        TextView res = dilaog01.findViewById(R.id.res_inform);
                        TextView ser = dilaog01.findViewById(R.id.ser_num);



                        close_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dilaog01.dismiss();
                            }
                        });

                    }
                });

                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(searchBox.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "검색할 책이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            result.clear();
                            searchData(searchBox.getText().toString());
                        }
                    }
                });
            }
        });
    }

//    public void writeData(BookDataVO data) {
//        mDatabase.child("bookList").child("3").setValue(data);
//    }

    public ArrayList<ListData> initListData(List<BookDataVO> result) {
        listViewData.clear();
        for (BookDataVO tmp : result) {
            ListData listData = new ListData();

            listData.mainImage = R.drawable.ic_launcher_foreground;
            listData.star = R.drawable.ic_launcher_foreground;
            listData.title = tmp.getBookName();
            listData.body_1 =  tmp.getPublisher() + " / " + tmp.getAuthor() + " / " + tmp.getPrice() +"원";
            listData.body_2 = tmp.getPublishedDate() + " / " + tmp.getIsbn() + " / " + tmp.getQuantitiy() +"권 남음";


            listViewData.add(listData);
        }
        return listViewData;
    }

    List<BookDataVO> searchedResult = new LinkedList<>();
    public void searchData (String searchText) {

        mDatabase.child("bookList").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                Log.d("firebase", String.valueOf(task.getResult().getValue()));

                searchedResult.clear();

                System.err.println("searchText===>" + searchText);

                for (DataSnapshot ds : task.getResult().getChildren()) {
                    BookDataVO tmp = ds.getValue(BookDataVO.class);
                    if(tmp.getBookName().equals(searchText)) {
                        searchedResult.add(tmp);
                    }
                }
                ListAdapter aAdapter = new CustomListView(initListData(searchedResult));
                listView.setAdapter(aAdapter);
            }
        });
    }
}