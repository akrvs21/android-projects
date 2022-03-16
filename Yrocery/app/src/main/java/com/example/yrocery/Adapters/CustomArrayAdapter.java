package com.example.yrocery.Adapters;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
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
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
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
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.productName.setText(rowItem.getName());
        holder.productPrice.setText(rowItem.getPrice());
//        holder.productImg.setImageURI(rowItem.getImgUrl());
        new ImageLoadTask(rowItem.getImage(), holder.productImg).execute();
        //holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }
}