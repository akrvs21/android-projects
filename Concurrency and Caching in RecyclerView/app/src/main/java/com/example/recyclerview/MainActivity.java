package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
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
    private LruCache<String, Bitmap> memoryCache; // create LruCache to store bitmap values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.mRecyclerView);
        Context context = this;

        initCache(); // initialize cache when activity gets created

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
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(context, users, memoryCache); // pass memoryCache to adapter
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<UserWrapper> call, Throwable t) {
                Toast.makeText(MainActivity.this, t + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initCache() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }
}
