package com.example.yrocery.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yrocery.POJO.Product;
import com.example.yrocery.R;
import com.example.yrocery.Utils.ImageLoadTask;

import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {
    ArrayList<Product> productList;
    Context context;
    LayoutInflater inflater;

    public CustomBaseAdapter(Context context, ArrayList<Product>productList) {
        this.context = context;
        this.productList = productList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.checkout_custom_row, null);
        TextView productName = view.findViewById(R.id.finalProductName);
        TextView productPrice = view.findViewById(R.id.finalProductPrice);
        ImageView productImage = view.findViewById(R.id.finalProductImage);

        productName.setText(productList.get(i).getName());
        productPrice.setText(productList.get(i).getAmount() + " x " + productList.get(i).getPrice());
        new ImageLoadTask(productList.get(i).getImage(), productImage).execute();
        return view;
    }
}
