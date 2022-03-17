package com.example.yrocery.Adapters;

import android.annotation.SuppressLint;
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

import com.example.yrocery.Fragments.Cart;
import com.example.yrocery.POJO.CardItem;
import com.example.yrocery.POJO.Product;
import com.example.yrocery.R;
import com.example.yrocery.Utils.ImageLoadTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class CartCustomArrayAdapter extends ArrayAdapter<Product> {
    Context context;
    String userPhone;
    private DatabaseReference mDatabaseReference;
    private ArrayList<String> cartProductkey = new ArrayList<String>();

    public CartCustomArrayAdapter(@NonNull Context context, int resource, ArrayList<Product> objects, String userPhone, ArrayList<String> cartProductkey) {
        super(context, resource, objects);
        Log.d("Adapter", "CustomArrayAdapter");
        Log.d("Adapter", String.valueOf(context));
        Log.d("Adapter", String.valueOf(resource));
        Log.d("Adapter", String.valueOf(objects));
        this.userPhone = userPhone;
        this.context = context;
        this.cartProductkey = cartProductkey;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView cartProductName;
        ImageView cartProductImg;
        TextView cartProductPrice;
        Button cartDeleteBtn;
        EditText cartProductAmount;
    }

    @SuppressLint("InflateParams")
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
            Log.d("getview", "if called");
            convertView = mInflater.inflate(R.layout.cart_custom_row, null);
            holder = new ViewHolder();
            holder.cartProductName = convertView.findViewById(R.id.cartProductName);
            holder.cartProductPrice = convertView.findViewById(R.id.cartProductPrice2);
            holder.cartProductImg = convertView.findViewById(R.id.cartProductImg);
            holder.cartDeleteBtn = convertView.findViewById(R.id.cartDeleteBtn);
            holder.cartProductAmount = convertView.findViewById(R.id.cartProductAmount);
            convertView.setTag(holder);
        } else
            Log.d("getview", "else called");
            holder = (ViewHolder) convertView.getTag();

        name = rowItem.getName();
        price = rowItem.getPrice();
        image = rowItem.getImage();
        productAmount[0] = rowItem.getAmount();
        Log.d("testing", name + " " + price + " " + productAmount[0] + " " + image);
        holder.cartProductName.setText(rowItem.getName());
        holder.cartProductPrice.setText(rowItem.getPrice());
        holder.cartProductAmount.setText(rowItem.getAmount());
        new ImageLoadTask(rowItem.getImage(), holder.cartProductImg).execute();
        ViewHolder finalHolder = holder;

        holder.cartDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("phonum", cartProductkey.get(position));
//                Cart.deleted = true;
                mDatabaseReference.child(userPhone).child("cartItems").child(cartProductkey.remove(position)).removeValue();
            }
        });
        return convertView;
    }
}
