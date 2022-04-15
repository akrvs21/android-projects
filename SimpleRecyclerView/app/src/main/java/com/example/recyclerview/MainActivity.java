package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> users;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.mRecyclerView);
        Context context = this;

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://dummyapi.io/data/v1/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        CallAPI callAPI = retrofit.create(CallAPI.class);

        Call<UserWrapper> call = callAPI.getUsers();

        // asynchronously makes network request
        call.enqueue(new Callback<UserWrapper>() {
            @Override
            public void onResponse(Call<UserWrapper> call, Response<UserWrapper> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, String.valueOf(response.body().getUsers().get(0).getFirstName()));
                // Get the data to display
                users = response.body().getUsers();
//                // Get the adapter
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(context, users);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<UserWrapper> call, Throwable t) {
                Toast.makeText(MainActivity.this, t + "", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
