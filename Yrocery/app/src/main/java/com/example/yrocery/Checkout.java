package com.example.yrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.yrocery.Adapters.CustomBaseAdapter;
import com.example.yrocery.POJO.Product;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {
    private ArrayList<Product> productList = new ArrayList<>();
    ListView productListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        productList = (ArrayList<Product>) getIntent().getSerializableExtra("productList");

        productListView = findViewById(R.id.productListView);
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), productList);
        productListView.setAdapter(customBaseAdapter);

    }
}