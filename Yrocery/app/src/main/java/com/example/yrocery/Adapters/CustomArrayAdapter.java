package com.example.yrocery.Adapters;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.yrocery.POJO.CardItem;
import com.example.yrocery.POJO.Product;
import com.example.yrocery.R;
import com.example.yrocery.Utils.ImageLoadTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class CustomArrayAdapter extends ArrayAdapter<Product> {
    Context context;
    String userPhone;
    private DatabaseReference mDatabaseReference;
    static int i = 1;

    public CustomArrayAdapter(@NonNull Context context, int resource,  ArrayList<Product> objects, String userPhone) {
        super(context, resource, objects);
        Log.d("Adapter", "CustomArrayAdapter");
        Log.d("Adapter", String.valueOf(context));
        Log.d("Adapter", String.valueOf(resource));
        Log.d("Adapter", String.valueOf(objects));
        this.userPhone = userPhone;
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView productName;
        ImageView productImg;
        TextView productPrice;
        Button addToCartBtn;
        EditText productAmount;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        String name;
        String price;
        final String[] productAmount = new String[1];
        String image;

        mDatabaseReference = FirebaseDatabase.getInstance("https://yrocery-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User");

        Product rowItem = getItem(position);
        Log.d("Adapter", "getView");
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row, null);
            holder = new ViewHolder();
            holder.productName = convertView.findViewById(R.id.productName);
            holder.productPrice = convertView.findViewById(R.id.productPrice2);
            holder.productImg = convertView.findViewById(R.id.productImg);
            holder.addToCartBtn = convertView.findViewById(R.id.cardBtn);
            holder.productAmount = convertView.findViewById(R.id.productAmount);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        name = rowItem.getName();
        price = rowItem.getPrice();
        image = rowItem.getImage();

        holder.productName.setText(rowItem.getName());
        holder.productPrice.setText(rowItem.getPrice());
        new ImageLoadTask(rowItem.getImage(), holder.productImg).execute();
        ViewHolder finalHolder = holder;
        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uniqueID = UUID.randomUUID().toString();
                productAmount[0] = finalHolder.productAmount.getText().toString();
                CardItem cardItem = new CardItem(name, price, image, productAmount[0]);
                mDatabaseReference.child(userPhone).child("cartItems").child(uniqueID).setValue(cardItem);

                Log.d("productId", "id: " + view.getId());
                Log.d("cardItem", "name: " + cardItem.getName());
                Log.d("cardItem", "price: " + cardItem.getPrice());
                Log.d("cardItem", "amount: " + cardItem.getAmount());
            }
        });

        return convertView;
    }
}
