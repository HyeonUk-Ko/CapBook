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
import java.util.ArrayList;

public class homefragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_homefragment, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView= view.findViewById(R.id.list);

        ArrayList<ListData> listViewData = new ArrayList<>();
        for (int i = 0; i < 30; ++i) {
            ListData listData = new ListData();

            listData.mainImage = R.drawable.ic_launcher_foreground;
            listData.star = R.drawable.ic_launcher_foreground;

            listData.title = " 책" + (i + 1);
            listData.body_1 = "OO출판사 / OOO";
            listData.body_2 = "2019.10.13 / 9461754";

            listViewData.add(listData);
        }

        ListAdapter aAdapter = new CustomListView(listViewData);
        listView.setAdapter(aAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}