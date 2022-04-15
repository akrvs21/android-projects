package com.example.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> users = new ArrayList<>();
    Bitmap bitmap;
    public RecyclerViewAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users; // This is array that contains the list of object, each object is type of User.
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // In onCreateViewHolder we inflate the layout and give a look to each of our rows
        // as you can see, we referencing custom_row.xml, from the Step #1, in inflate method
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);

        return new RecyclerViewAdapter.MyViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values to the views we created in the custom_row layout file, based on the position of the recycler view
        // Since RecyclerView reuses rows on Scroll Up/Down, onBindViewHolder gets called each time the row appears
        Picasso.get().load(users.get(position).getUserImage()).into(holder.userImage); // Picasso is a library to easily load images
        holder.userFullName.setText(users.get(position).getTitle() + ". " + users.get(position).getFirstName() + " " + users.get(position).getLastName()); // Setting text to TextView
        holder.userId.setText("My ID: " + users.get(position).getUserId());
    }

    @Override
    public int getItemCount() {
        // RecyclerView wants to know the count of items in the list
        return this.users.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing views from our custom_row layout file, similar what we normally do in onCreate method
        ImageView userImage;
        TextView userFullName;
        TextView userId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userFullName = itemView.findViewById(R.id.fullNameText);
            userId = itemView.findViewById(R.id.userId);
        }
    }
}
