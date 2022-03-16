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
import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Product> {
    Context context;
    public CustomArrayAdapter(@NonNull Context context, int resource,  ArrayList<Product> objects) {
        super(context, resource, objects);
        Log.d("Adapter", "CustomArrayAdapter");
        Log.d("Adapter", String.valueOf(context));
        Log.d("Adapter", String.valueOf(resource));
        Log.d("Adapter", String.valueOf(objects));
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
        String productName;
        String productPrice;
        final String[] productAmount = new String[1];
        String productImg;

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

        productName = rowItem.getName();
        productPrice = rowItem.getPrice();
        productImg = rowItem.getImage();

        holder.productName.setText(rowItem.getName());
        holder.productPrice.setText(rowItem.getPrice());
        new ImageLoadTask(rowItem.getImage(), holder.productImg).execute();

        ViewHolder finalHolder = holder;
        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productAmount[0] = finalHolder.productAmount.getText().toString();

                CardItem cardItem = new CardItem(productName, productPrice, productImg, productAmount[0]);
                Log.d("cardItem", "name: " + cardItem.getProductName());
                Log.d("cardItem", "price: " + cardItem.getProductPrice());
                Log.d("cardItem", "amount: " + cardItem.getProductAmount());
            }
        });

        return convertView;
    }
}
