package com.example.ourgroupbooksystem.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.ourgroupbooksystem.R;


public class homefragment extends Fragment {

    private View view;
    private GridView gridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_homefragment, container, false);
    }
}