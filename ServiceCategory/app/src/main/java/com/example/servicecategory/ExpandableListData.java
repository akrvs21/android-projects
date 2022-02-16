package com.example.servicecategory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListData {
    private static final String TABLE_NAME1 = "category";
    private static final String TABLE_NAME2 = "services";

    static HashMap<String, List<String>> getTitles() {

        HashMap<String, List<String>> listDetail = new HashMap<>();
        List<String> europe = new ArrayList<>();
        europe.add("Germany");
        europe.add("France");
        europe.add("Belarus");
        europe.add("Spain");
        europe.add("Italy");
        europe.add("Sweden");
        europe.add("Hungary");

        List<String> asia = new ArrayList<>();
        asia.add("China");
        asia.add("Thailand");
        asia.add("Indonesia");
        asia.add("Philipines");
        asia.add("Nepal");

        List<String> ca = new ArrayList<>();
        ca.add("Tajikistan");
        ca.add("Uzbekistan");
        ca.add("Turkmenistan");
        ca.add("Kyrgizistan");
        ca.add("Kazakhstan");

        listDetail.put("Europe countries", europe);
        listDetail.put("Asian countries", asia);
        listDetail.put("Central Asian countries", ca);

        return listDetail;
    }

}
