package com.example.yrocery.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yrocery.Adapters.CartCustomArrayAdapter;
import com.example.yrocery.Menu;
import com.example.yrocery.POJO.Product;
import com.example.yrocery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cart extends ListFragment {
    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<String> cartProductkey = new ArrayList<String>();
    private CartCustomArrayAdapter mAdapter;
    public static Boolean deleted;
    String userPhone;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Getting user phone number from parent activity
        Menu menuActivity = (Menu) getActivity();
        userPhone = menuActivity.getUserPhone();
        deleted = false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("INFruits", userPhone);
        // DB initialization
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://yrocery-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference table_cartItems = database.getReference("User").child(userPhone).child("cartItems");
        // Listener to products table, reads and listens to/the DB
            table_cartItems.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if(!deleted) {
                        productList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                        cartProductkey.add(ds.getKey());
                        productList.add(snapshot.child(ds.getKey()).getValue(Product.class));
                    }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new CartCustomArrayAdapter(getActivity(), R.layout.cart_custom_row, productList, userPhone, cartProductkey);
                                setListAdapter(mAdapter);
                            }
                        });
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        Log.d("Fruits", "onCreateView: ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}