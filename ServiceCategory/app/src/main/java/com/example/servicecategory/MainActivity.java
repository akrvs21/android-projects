package com.example.servicecategory;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
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
    List<String> listPaymentInfo;
    HashMap<String, List<String>> listDetail;
    HashMap<String, List<String>>listDetails;
    JSONArray cats = null;
    JSONArray services = null;
    private DBHandler dbHandler;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isDelete =  getApplicationContext().deleteDatabase("allservices");
        Log.d("del", String.valueOf(isDelete));
        dbHandler = new DBHandler(MainActivity.this);
        progressBar = findViewById(R.id.progressBar);

        String str = LoaderHelper.parseFileToString(this, "response.json");
//        Log.d("jsonstring", str);

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jsonstring", String.valueOf(jsonObj));
        // Getting JSONArray category from the JSONObject
        try {
            cats = jsonObj.getJSONArray("cats");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Getting JSONArray services from the JSONObject
        try {
            services = jsonObj.getJSONArray("services");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("main", String.valueOf(services));

        // Iterate through cats array and call a function to add to DB
        for(int i = 0; i < cats.length(); i++) {
            try {
                JSONObject singleObject = cats.getJSONObject(i);
                dbHandler.insertData(singleObject.getInt("catid"), singleObject.getString("catname"), singleObject.getString("catnameTJ"), singleObject.getString("catnameEN"), "category" );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("length", String.valueOf(cats.length()));
        // Iterate through services array and call a function to add to DB
        for(int i = 0; i < (services != null ? services.length() : 0); i++) {
            try {
                JSONObject singleObject = services.getJSONObject(i);
                dbHandler.insertData(singleObject.getInt("catid"), singleObject.getString("servicename"), singleObject.getString("paymin"), singleObject.getString("paymax"), "services");
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
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                List<Integer> indexes = new ArrayList<>();
                indexes.add(groupPos);
                indexes.add(childPos);

                CustomAsyncTask customAsyncTask = new CustomAsyncTask();
                customAsyncTask.execute(indexes);

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private class CustomAsyncTask extends AsyncTask<List, Void, List> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List doInBackground(List... lists) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int groupPos = (int) lists[0].get(0);
            int childPos = (int) lists[0].get(1);
            Log.d("tagg", String.valueOf(groupPos));
            Log.d("tagg", String.valueOf(childPos));
            String str = listDetails.get(listTitles.get(groupPos)).get(childPos);

            Log.d("localstr", str);
            listPaymentInfo = dbHandler.readPaymentInfo(str);
            listPaymentInfo.add(str);
            Log.d("localstr", String.valueOf(listPaymentInfo));

            return listPaymentInfo;
        }

        @Override
        protected void onPostExecute(List listPaymentInfo) {
            super.onPostExecute(listPaymentInfo);

            Intent intent = new Intent(MainActivity.this, ServiceInfoActivity.class);
            intent.putExtra("title", (String) listPaymentInfo.get(2));
            intent.putExtra("paymin", (String) listPaymentInfo.get(0));
            intent.putExtra("paymax", (String) listPaymentInfo.get(1));
//
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
        }
    }
}