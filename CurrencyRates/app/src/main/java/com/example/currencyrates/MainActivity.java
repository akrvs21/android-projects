package com.example.currencyrates;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private ImageView logo, shine;
    private ConstraintLayout constraintLayout;
    // Footer
    private TextView companyInfo;
    // Create the Handler
    private Handler handler = new Handler();


    JSONObject jsonObject = null;
    JSONObject transferObjBuy = null;
    JSONObject transferObjSell = null;
    JSONObject exchangeObjBuy = null;
    JSONObject exchangeObjSell = null;
    JSONObject stringObject = null;

    TransferBuy transferBuy = null;
    TransferSell transferSell = null;
    ExchangeBuy exchangeBuy = null;
    ExchangeSell exchangeSell = null;
    Strings titles = null;

    private String [] endpoints = new String[] {"https://payvand.tj/api/update-rates?locale=en", "https://payvand.tj/api/update-rates?locale=ru", "https://payvand.tj/api/update-rates?locale=tj"};

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

        dateText = findViewById(R.id.dateText);
        date = findViewById(R.id.date);
        timeText = findViewById(R.id.timeText);
        time = findViewById(R.id.time);
        companyInfo = findViewById(R.id.companyInfo);
        logo = findViewById(R.id.imageView);
        shine = findViewById(R.id.shine);
        constraintLayout = findViewById(R.id.rootLayout);

        // Start the Runnable immediately
        handler.post(runnable);

//        getFreshRates(endpoints[1]);
        Log.d("forloop", "test1");
        Log.d("forloop", String.valueOf(i));
//        while(true) {
//            Log.d("forloop", "test2");
//            for (int i = 0; i < endpoints.length; i++) {
//                getFreshRates(endpoints[i]);
//                Log.d("forloop", String.valueOf(i));
//                try {
//                    TimeUnit.SECONDS.sleep(30);
//                } catch (InterruptedException ie) {
//                    Thread.currentThread().interrupt();
//                }
//                if(i == 2) {
//                    i = -1;
//                }
//            }
//        }
    }

    public void getFreshRates (String url) {
        OkHttpClient client = new OkHttpClient();
//        String url = "https://payvand.tj/api/update-rates?locale=en";
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        Date currDate = new Date();
        Date currTime = new Date();
        Log.d("today", dateFormatter.format(currDate));
        Log.d("today", timeFormatter.format(currTime));

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
                        stringObject = jsonObject.getJSONObject("strings");
                        // Map JSON data to POJO fields
                        transferBuy = mapper.readValue(transferObjBuy.toString(), TransferBuy.class);
                        transferSell = mapper.readValue(transferObjSell.toString(), TransferSell.class);
                        titles = mapper.readValue(stringObject.toString(), Strings.class);
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
                        @SuppressLint({"DefaultLocale", "SetTextI18n"})
                        @Override
                        public void run() {
                            startShineAnim();
                            Log.d("logd", myResponse);
                            // Setting values for Transfer table
                            transferBuyUsd.setText(String.format("%.4f", transferBuy.getUsd()));
                            transferBuyEur.setText(String.format("%.4f", transferBuy.getEur()));
                            transferBuyRub.setText(String.format("%.4f", transferBuy.getRur()));
                            transferSellUsd.setText(String.format("%.4f", transferSell.getUsd()));
                            transferSellEur.setText(String.format("%.4f", transferSell.getEur()));
                            transferSellRub.setText(String.format("%.4f", transferSell.getRur()));

                            // Setting values for Exchange table
                            exchangeBuyUsd.setText(String.format("%.4f", exchangeBuy.getUsd()));
                            exchangeBuyEur.setText(String.format("%.4f", exchangeBuy.getEur()));
                            exchangeBuyRub.setText(String.format("%.4f", exchangeBuy.getRur()));
                            exchangeSellUsd.setText(String.format("%.4f", exchangeSell.getUsd()));
                            exchangeSellEur.setText(String.format("%.4f", exchangeSell.getEur()));
                            exchangeSellRub.setText(String.format("%.4f", exchangeSell.getRur()));

                            // Setting titles
                            transferTitle.setText(titles.getTitle_transfer());
                            transferBuyText.setText(titles.getBuy());
                            transferSellText.setText(titles.getSell());
                            exchangeTitle.setText(titles.getTitle_exchange());
                            exchangeBuyText.setText(titles.getBuy());
                            exchangeSellText.setText(titles.getSell());
                            dateText.setText(titles.getDate() + ":");
                            timeText.setText(titles.getTime() + ":");
                            companyInfo.setText(titles.getCompany_info());
                            date.setText(dateFormatter.format(currDate));
                            time.setText(timeFormatter.format(currTime));
                        }
                    });
                }
            }
        });
    }
    static int i = 0;
    // Define the code block to be executed
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            Log.d("statici", String.valueOf(i));
            getFreshRates(endpoints[i]);
                i++;
                if(i == 3) {
                    i = 0;
                }
            // Repeat every 2 seconds
            handler.postDelayed(runnable, 30000);
        }
    };

    private void startShineAnim() {
        Animation animation = new TranslateAnimation(
                0, constraintLayout.getWidth() + shine.getWidth(), 0, 0
        );
        animation.setDuration(550);
        animation.setFillAfter(true);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        shine.startAnimation(animation);
    }
}