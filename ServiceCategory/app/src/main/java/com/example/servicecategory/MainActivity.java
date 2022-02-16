package com.example.servicecategory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> listTitles;
    HashMap<String, List<String>> listDetail;
    HashMap<String, List<String>>listDetails;
    JSONArray cats = null;
    JSONArray services = null;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isDelete =  getApplicationContext().deleteDatabase("allservices");
        Log.d("del", String.valueOf(isDelete));
        dbHandler = new DBHandler(MainActivity.this);

        String str = LoaderHelper.parseFileToString(this, "response.json");
        Log.d("json", str);

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Getting JSONArray from the JSONObject
        try {
            cats = jsonObj.getJSONArray("cats");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Getting JSONArray from the JSONObject
        try {
            services = jsonObj.getJSONArray("services");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.d("json", String.valueOf(cats.getJSONObject(1)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("main", String.valueOf(services));

        // Iterate through cats array and call a function to add to DB
        for(int i = 0; i < cats.length(); i++) {
            try {
                JSONObject singleObject = cats.getJSONObject(i);
                dbHandler.insertData(singleObject.getInt("catid"), singleObject.getString("catname"), "category" );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("length", String.valueOf(cats.length()));
        // Iterate through services array and call a function to add to DB
        for(int i = 0; i < (services != null ? services.length() : 0); i++) {
            try {
                JSONObject singleObject = services.getJSONObject(i);
                dbHandler.insertData(singleObject.getInt("catid"), singleObject.getString("servicename"), "services");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        expandableListView = findViewById(R.id.expendableList);

        listTitles = dbHandler.readTitles();
        listDetails = dbHandler.readListDetail();

        Log.d("titles", String.valueOf(listTitles));
        Log.d("titles", String.valueOf(listDetails));
        expandableListAdapter = new CustomExpandableListAdapter(this, listTitles, listDetails);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
//                Toast.makeText(getApplicationContext(), listTitles.get(i) + " List Expanded.", Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
//                Toast.makeText(getApplicationContext(), listTitles.get(i) + "List Collapsed.", Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                Toast.makeText(getApplicationContext(), listTitles.get(i) + " -> " + listDetail.get(listTitles.get(i)).get(i1), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.load_data:
//                Toast.makeText(getApplicationContext(), "Data is loading", Toast.LENGTH_SHORT).show();
//                // Iterate through cats array and call a function to add to DB
//                for(int i = 0; i < cats.length(); i++) {
//                    try {
//                        JSONObject singleObject = cats.getJSONObject(i);
//                        dbHandler.insertData(singleObject.getInt("catid"), singleObject.getString("catname"), "category" );
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Log.d("length", String.valueOf(cats.length()));
//                // Iterate through services array and call a function to add to DB
//                for(int i = 0; i < (services != null ? services.length() : 0); i++) {
//                    try {
//                        JSONObject singleObject = services.getJSONObject(i);
//                        dbHandler.insertData(singleObject.getInt("catid"), singleObject.getString("servicename"), "services");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                expandableListView = findViewById(R.id.expendableList);
////        listDetail = ExpandableListData.getChildList();
////        listTitles = new ArrayList<>(listDetail.keySet());
//                listTitles = dbHandler.readTitles();
//                listDetails = dbHandler.readListDetail();
//                Log.d("titles", String.valueOf(listTitles));
//                Log.d("titles", String.valueOf(listDetails));
//                expandableListAdapter = new CustomExpandableListAdapter(this, listTitles, listDetails);
//                expandableListView.setAdapter(expandableListAdapter);
//
//                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//                    @Override
//                    public void onGroupExpand(int i) {
////                Toast.makeText(getApplicationContext(), listTitles.get(i) + " List Expanded.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//                    @Override
//                    public void onGroupCollapse(int i) {
////                Toast.makeText(getApplicationContext(), listTitles.get(i) + "List Collapsed.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//                    @Override
//                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
////                Toast.makeText(getApplicationContext(), listTitles.get(i) + " -> " + listDetail.get(listTitles.get(i)).get(i1), Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });
//            break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + item.getItemId());
//        }
//        return true;
//    }
}