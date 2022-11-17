package com.example.ourgroupbooksystem.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ourgroupbooksystem.R;

import java.util.ArrayList;

public class homefragment extends Fragment {

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homefragment);

        listView = listView.findViewById(R.id.listview);

        ArrayList<ListData> listViewData = new ArrayList<>();
        for (int i=0; i<30; ++i)
        {
            ListData listData = new ListData();

            listData.mainImage = R.drawable.ic_launcher_foreground;
            listData.star = R.drawable.ic_launcher_foreground;

            listData.title = " 책"+(i+1);
            listData.body_1 = "OO출판사 / OOO";
            listData.body_2 = "2019.10.13 / 9461754";

            listViewData.add(listData);
        }


        ListAdapter oAdapter = new CustomListView(listViewData);
        listView.setAdapter(oAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickName = listViewData.get(position).title;
                Log.d("확인","name : "+clickName);
            }
        });
    }

    private void setContentView(int fragment_homefragment) {

    }

}