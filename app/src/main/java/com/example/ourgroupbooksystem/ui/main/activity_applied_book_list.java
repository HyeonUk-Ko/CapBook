package com.example.ourgroupbooksystem.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourgroupbooksystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class activity_applied_book_list extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private List<BookDataVO> result = new LinkedList<BookDataVO>();
    ArrayList<ListData> listViewData = new ArrayList<>();
    ListView listView;
    Dialog applyBookDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_book_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        applyBookDialog = new Dialog(activity_applied_book_list.this);       // Dialog 초기화
        applyBookDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        applyBookDialog.setContentView(R.layout.dialog_for_apply_board);

        listView= findViewById(R.id.list2);
        Button searchBtn = (Button) findViewById(R.id.searchBtn2);
        EditText searchBox = (EditText) findViewById(R.id.searchBox2);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("applyBook").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

                        Button close_btn = applyBookDialog.findViewById(R.id.btn_close);
                        Button up_btn = applyBookDialog.findViewById(R.id.update);
                        Button delete_btn = applyBookDialog.findViewById(R.id.delete);

                        TextView bookName = applyBookDialog.findViewById(R.id.book_name);
                        TextView quantity = applyBookDialog.findViewById(R.id.inform);
                        TextView publishedDate = applyBookDialog.findViewById(R.id.date_inform);
                        TextView publisher = applyBookDialog.findViewById(R.id.made_inform);
                        TextView price = applyBookDialog.findViewById(R.id.price_inform);
                        TextView author = applyBookDialog.findViewById(R.id.author);
                        TextView isbn = applyBookDialog.findViewById(R.id.ser_num);
                        String data = listViewData.get(position).body_2;
                        String[] tmpArray = data.split("/");
                        String isbnDataInList = tmpArray[1].toString();

                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("/");

                        DatabaseReference bookListRef = ref;

                        bookListRef.child("applyBook").orderByChild("isbn").equalTo(isbnDataInList).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                BookDataVO popupData = snapshot.getValue(BookDataVO.class);

                                bookName.setText(popupData.getBookName());
                                quantity.setText(String.valueOf(popupData.getQuantitiy()));
                                publishedDate.setText(popupData.getPublishedDate());
                                publisher.setText(popupData.getPublisher());
                                price.setText(String.valueOf(popupData.getPrice()));
                                author.setText(popupData.getAuthor());
                                isbn.setText(popupData.getIsbn());

                                applyBookDialog.show();
                                Window window = applyBookDialog.getWindow();
                                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        close_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                applyBookDialog.dismiss();
                            }
                        });

                        delete_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteData(isbnDataInList);
                                Toast.makeText(activity_applied_book_list.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                                applyBookDialog.dismiss();
                                refreshList();
                            }
                        });

                    }
                });

                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(searchBox.getText().toString().equals("")){
                            Toast.makeText(activity_applied_book_list.this, "검색할 책이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            result.clear();
                            searchData(searchBox.getText().toString());
                        }
                    }
                });
            }
        });

    }

    public ArrayList<ListData> initListData(List<BookDataVO> result) {
        listViewData.clear();
        for (BookDataVO tmp : result) {
            ListData listData = new ListData();

            listData.mainImage = R.drawable.book_icon3;
            listData.title = tmp.getBookName();
            listData.body_1 =  tmp.getPublisher() + " /" + tmp.getAuthor() + "/ " + tmp.getPrice() +"원";
            listData.body_2 = tmp.getPublishedDate() + " /" + tmp.getIsbn() + "/ " + tmp.getQuantitiy() +"권 신청";


            listViewData.add(listData);
        }
        return listViewData;
    }

    List<BookDataVO> searchedResult = new LinkedList<>();

    public void searchData (String searchText) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("/");

        DatabaseReference bookListRef = ref.child("applyBook");
        searchedResult.clear();

        bookListRef.orderByChild("applyBook").equalTo(searchText).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                searchedResult.add(snapshot.getValue(BookDataVO.class));
                ListAdapter aAdapter = new CustomListView(initListData(searchedResult));
                listView.setAdapter(aAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void deleteData (String isbn) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("/");

        DatabaseReference bookListRefForDelete = ref.child("applyBook/" + isbn);
        BookDataVO noData = new BookDataVO(null, null, null, null, null, null, null);
        bookListRefForDelete.setValue(noData);
    }

    public void refreshList () {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("/");

        DatabaseReference bookListRef = ref.child("applyBook");
        searchedResult.clear();

        bookListRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    Toast.makeText(activity_applied_book_list.this, "더 이상 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    bookListRef.orderByChild("applyBook").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            searchedResult.add(snapshot.getValue(BookDataVO.class));
                            ListAdapter aAdapter = new CustomListView(initListData(searchedResult));
                            listView.setAdapter(aAdapter);
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }
}