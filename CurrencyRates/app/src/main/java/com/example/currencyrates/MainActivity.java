package com.example.currencyrates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.window.SplashScreen;
import android.window.SplashScreenView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.JsonIterator;
import com.jsoniter.annotation.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInput;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends FragmentActivity {
    // Transfer table variables
    private TextView transferTitle, transferBuyText, transferSellText, transferBuyUsd, transferSellUsd, transferBuyEur, transferSellEur, transferBuyRub, transferSellRub;
    // Exchange table variables
    private TextView exchangeTitle, exchangeBuyText, exchangeSellText, exchangeBuyUsd, exchangeSellUsd, exchangeBuyEur, exchangeSellEur, exchangeBuyRub, exchangeSellRub;
    // Header
    private TextView dateText, date, timeText, time;

    JSONObject jsonObject = null;
    JSONObject transferObjBuy = null;
    JSONObject transferObjSell = null;
    JSONObject exchangeObjBuy = null;
    JSONObject exchangeObjSell = null;

    TransferBuy transferBuy = null;
    TransferSell transferSell = null;
    ExchangeBuy exchangeBuy = null;
    ExchangeSell exchangeSell = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transferTitle = findViewById(R.id.transferTitle);
        transferBuyText = findViewById(R.id.transferBuyText);
        transferSellText = findViewById(R.id.transferSellText);
        transferBuyUsd = findViewById(R.id.transferBuyUsd);
        transferSellUsd = findViewById(R.id.transferSellUsd);
        transferBuyEur = findViewById(R.id.transferBuyEur);
        transferSellEur = findViewById(R.id.transferSellEur);
        transferBuyRub = findViewById(R.id.transferBuyRub);
        transferSellRub = findViewById(R.id.transferSellRub);

        exchangeTitle = findViewById(R.id.exchangeTitle);
        exchangeBuyText = findViewById(R.id.exchangeBuyText);
        exchangeSellText = findViewById(R.id.exchangeSellText);
        exchangeBuyUsd = findViewById(R.id.exchangeBuyUsd);
        exchangeSellUsd = findViewById(R.id.exchangeSellUsd);
        exchangeBuyEur = findViewById(R.id.exchangeBuyEur);
        exchangeSellEur = findViewById(R.id.exchangeSellEur);
        exchangeBuyRub = findViewById(R.id.exchangeBuyRub);
        exchangeSellRub = findViewById(R.id.exchangeSellRub);


        OkHttpClient client = new OkHttpClient();
        String url = "https://payvand.tj/api/update-rates?locale=en";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        jsonObject = new JSONObject(myResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Getting "transfer rate" data
                    try {
                        // A mapper to map  Json to POJO
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        // Getting "buy" and "sell" JSONObjects
                        transferObjBuy = jsonObject.getJSONObject("rates").getJSONObject("transfer").getJSONObject("buy");
                        transferObjSell = jsonObject.getJSONObject("rates").getJSONObject("transfer").getJSONObject("sell");
                        // Map JSON data to POJO fields
                        transferBuy = mapper.readValue(transferObjBuy.toString(), TransferBuy.class);
                        transferSell = mapper.readValue(transferObjSell.toString(), TransferSell.class);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Getting "exchange rate" data
                    try {
                        // A mapper to map  Json to POJO
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        // Getting "buy" and "sell" JSONObjects
                        exchangeObjBuy = jsonObject.getJSONObject("rates").getJSONObject("exchange").getJSONObject("buy");
                        exchangeObjSell = jsonObject.getJSONObject("rates").getJSONObject("exchange").getJSONObject("sell");
                        // Map JSON data to POJO fields
                        exchangeBuy = mapper.readValue(exchangeObjBuy.toString(), ExchangeBuy.class);
                        exchangeSell = mapper.readValue(exchangeObjSell.toString(), ExchangeSell.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("logd", myResponse);
                            // Setting values for Transfer table
                            transferBuyUsd.setText(String.valueOf(transferBuy.getUsd()));
                            transferBuyEur.setText(String.valueOf(transferBuy.getEur()));
                            transferBuyRub.setText(String.valueOf(transferBuy.getRur()));
                            transferSellUsd.setText(String.valueOf(transferSell.getUsd()));
                            transferSellEur.setText(String.valueOf(transferSell.getEur()));
                            transferSellRub.setText(String.valueOf(transferSell.getRur()));

                            // Setting values for Exchange table
                            exchangeBuyUsd.setText(String.valueOf(exchangeBuy.getUsd()));
                            exchangeBuyEur.setText(String.valueOf(exchangeBuy.getEur()));
                            exchangeBuyRub.setText(String.valueOf(exchangeBuy.getRur()));
                            exchangeSellUsd.setText(String.valueOf(exchangeSell.getUsd()));
                            exchangeSellEur.setText(String.valueOf(exchangeSell.getEur()));
                            exchangeSellRub.setText(String.valueOf(exchangeSell.getRur()));
                        }
                    });
                }
            }
        });
    }
}