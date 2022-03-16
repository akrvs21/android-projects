package com.example.yrocery.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;

import com.example.yrocery.Adapters.CustomArrayAdapter;
import com.example.yrocery.POJO.Product;
import com.example.yrocery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fruits extends ListFragment {
    private ArrayList<Product> productList = new ArrayList<>();
    private CustomArrayAdapter mAdapter;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://yrocery-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference table_products = database.getReference("Fruits");
        Log.d("product", "onAttach: ");
        table_products.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i = 1; i <= snapshot.getChildrenCount(); i++) {
                    productList.add(snapshot.child(i + "").getValue(Product.class));
                }
                Log.d("product", "onDataChange: " + productList);
                mAdapter = new CustomArrayAdapter(getActivity(), R.layout.custom_row, productList);
                setListAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("product", "outter: " + productList);
//        mAdapter = new CustomArrayAdapter(getActivity(), R.layout.custom_row, productList);
//        setListAdapter(mAdapter);
        Log.d("Fruits", "onCreateView: ");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                inflater.getContext(),
//                R.layout.custom_row,
//                R.id.productName,
//                getResources().getStringArray(R.array.fruits));
//        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
