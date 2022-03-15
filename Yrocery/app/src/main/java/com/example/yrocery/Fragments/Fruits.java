package com.example.yrocery.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;

import com.example.yrocery.R;

public class Fruits extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                R.layout.custom_row,
                R.id.productName,
                getResources().getStringArray(R.array.fruits));
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}