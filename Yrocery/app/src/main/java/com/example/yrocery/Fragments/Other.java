package com.example.yrocery.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.yrocery.Adapters.CustomArrayAdapter;
import com.example.yrocery.Menu;
import com.example.yrocery.POJO.Product;
import com.example.yrocery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Other extends ListFragment {
    private ArrayList<Product> productList = new ArrayList<>();
    private CustomArrayAdapter mAdapter;
    String userPhone;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Getting user phone number from parent activity
        Menu menuActivity = (Menu) getActivity();
        userPhone = menuActivity.getUserPhone();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://yrocery-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference table_products = database.getReference("Other");
        table_products.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i = 1; i <= snapshot.getChildrenCount(); i++) {
                    productList.add(snapshot.child(i + "").getValue(Product.class));
                }
                Log.d("product", "onDataChange: " + productList);
                mAdapter = new CustomArrayAdapter(getActivity(), R.layout.custom_row, productList, userPhone);
                getListView().setDivider(null);
                setListAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getListView().setDivider(null);
    }
}
