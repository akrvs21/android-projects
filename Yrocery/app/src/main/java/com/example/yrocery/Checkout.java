package com.example.yrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yrocery.Adapters.CustomBaseAdapter;
import com.example.yrocery.POJO.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Checkout extends AppCompatActivity {
    private ArrayList<Product> productList = new ArrayList<>();
    ListView productListView;
    TextView totalPriceView;
    Button buyButton;
    EditText userNameField, userPhoneNumberField, userAddressField;
    String userName, userPhoneNumber, userAddress;
    private DatabaseReference mDatabaseReference;
    private String userPhone;
    int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mDatabaseReference = FirebaseDatabase.getInstance("https://yrocery-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User");
        productList = (ArrayList<Product>) getIntent().getSerializableExtra("productList");
        userPhone = getIntent().getStringExtra("userPhone");
        buyButton = findViewById(R.id.buyButton);
        totalPriceView = findViewById(R.id.totalPrice);
        productListView = findViewById(R.id.productListView);
        userNameField = findViewById(R.id.userName);
        userPhoneNumberField = findViewById(R.id.userPhoneNumber);
        userAddressField = findViewById(R.id.userAddress);

        for(int i = 0; i < productList.size(); i++) {
            totalPrice += Integer.parseInt(productList.get(i).getAmount()) * Integer.parseInt(productList.get(i).getPrice().replace("$", ""));
        }
        Log.d("totalPrice", String.valueOf(totalPrice));

        totalPriceView.setText("Total price: " + totalPrice);

        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), productList);
        productListView.setAdapter(customBaseAdapter);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uniqueID = UUID.randomUUID().toString();
                userName = userNameField.getText().toString();
                userPhoneNumber = userPhoneNumberField.getText().toString();
                userAddress = userAddressField.getText().toString();

                mDatabaseReference.child(userPhone).child("order").child(uniqueID).setValue(productList);
                mDatabaseReference.child(userPhone).child("order").child(uniqueID).child("contacts").child("name").setValue(userName);
                mDatabaseReference.child(userPhone).child("order").child(uniqueID).child("contacts").child("phone").setValue(userPhoneNumber);
                mDatabaseReference.child(userPhone).child("order").child(uniqueID).child("contacts").child("address").setValue(userAddress);
                mDatabaseReference.child(userPhone).child("order").child(uniqueID).child("totalPrice").setValue(totalPrice);

                mDatabaseReference.child(userPhone).child("cartItems").removeValue();

                Intent finishBuyIntent = new Intent(Checkout.this, BuyFinal.class);
                startActivity(finishBuyIntent);
            }
        });
    }
}






