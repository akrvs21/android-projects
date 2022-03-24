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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;

public class CartCustomArrayAdapter extends ArrayAdapter<Product> {
    Context context;
    String userPhone;
    private DatabaseReference mDatabaseReference;
    private ArrayList<String> cartProductkey = new ArrayList<String>();

    public CartCustomArrayAdapter(@NonNull Context context, int resource, ArrayList<Product> objects, String userPhone, ArrayList<String> cartProductkey) {
        super(context, resource, objects);
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
        TextView cartProductAmount;
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
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cart_custom_row, null);
            holder = new ViewHolder();
            holder.cartProductName = convertView.findViewById(R.id.cartProductName);
            holder.cartProductPrice = convertView.findViewById(R.id.cartProductPrice2);
            holder.cartProductImg = convertView.findViewById(R.id.cartProductImg);
            holder.cartDeleteBtn = convertView.findViewById(R.id.cartDeleteBtn);
            holder.cartProductAmount = convertView.findViewById(R.id.cartProductAmount);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        name = rowItem.getName();
        price = rowItem.getPrice();
        image = rowItem.getImage();
        productAmount[0] = rowItem.getAmount();

        holder.cartProductName.setText(name);
        holder.cartProductPrice.setText(price);
        holder.cartProductAmount.setText(productAmount[0]);
        new ImageLoadTask(image, holder.cartProductImg).execute();
        ViewHolder finalHolder = holder;

        holder.cartDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference.child(userPhone).child("cartItems").child(cartProductkey.remove(position)).removeValue();
            }
        });
        return convertView;
    }
}
