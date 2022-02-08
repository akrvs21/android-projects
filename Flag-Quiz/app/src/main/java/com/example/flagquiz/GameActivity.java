package com.example.flagquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameActivity extends AppCompatActivity {
    private TextView textViewCorrect, textViewWrong, textViewQuestion;
    private ImageView imageViewFlag, imageViewNext;
    private Button buttonA, buttonB, buttonC, buttonD;
    private int questionCount = 1;
    public String imgName = "";
    private int totalCorrect = 0;
    private int totalWrong = 0;
    private int successRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textViewCorrect = findViewById(R.id.textViewCorrect);
        textViewWrong = findViewById(R.id.textViewWrong);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        imageViewFlag = findViewById(R.id.imageViewFlag);
        imageViewNext = findViewById(R.id.imageViewNext);
        buttonA = findViewById(R.id.btnA);
        buttonB = findViewById(R.id.btnB);
        buttonC = findViewById(R.id.btnC);
        buttonD = findViewById(R.id.btnD);

        textViewQuestion.setText("Question: " + questionCount);
        textViewCorrect.setText("Correct:" + totalCorrect);
        textViewWrong.setText("Wrong:" + totalWrong);
        imageViewNext.setEnabled(false);
        loadQuestion();

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Button b = (Button)view;
                String btnText = b.getText().toString().replaceAll("\\s+","").toLowerCase();
                Log.d("Btn", btnText);

                if(btnText.equals(imgName)) {
                    b.setBackgroundColor(Color.GREEN);
                    ++totalCorrect;
                    textViewCorrect.setText("Correct:" + totalCorrect);
                    imageViewNext.setEnabled(true);
                } else {
                    b.setBackgroundColor(Color.RED);
                    totalWrong++;
                    textViewWrong.setText("Wrong:" + totalWrong);
                    imageViewNext.setEnabled(false);
                }
            }
        };
        buttonA.setOnClickListener(listener);
        buttonB.setOnClickListener(listener);
        buttonC.setOnClickListener(listener);
        buttonD.setOnClickListener(listener);

        imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionCount++;
                if(questionCount <= 20) {
                    loadQuestion();

                    buttonA.setBackgroundColor(Color.rgb(102,178,255));
                    buttonB.setBackgroundColor(Color.rgb(102,178,255));
                    buttonC.setBackgroundColor(Color.rgb(102,178,255));
                    buttonD.setBackgroundColor(Color.rgb(102,178,255));
                    textViewQuestion.setText("Question: " + questionCount);
                } else {
                    successRate = (int) (((double)totalCorrect / (totalCorrect + totalWrong) )* 100);
                    Log.d("rate", String.valueOf(successRate));
                    Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                    intent.putExtra("correct", totalCorrect);
                    intent.putExtra("wrong", totalWrong);
                    intent.putExtra("rate",successRate);
                    startActivity(intent);
                    finish();
                }
                imageViewNext.setEnabled(false);
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(getIntent());
//                overridePendingTransition(0, 0);
            }
        });
    }
    public void loadQuestion() {
        // make API call to server
        OkHttpClient client = new OkHttpClient();
        String url = "web service IP" + questionCount;

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
//                    Log.d("Myresp", myResponse);

                    myResponse = myResponse.substring(1, myResponse.length() - 1);
                    myResponse = myResponse.replaceAll("\\],\\[", "],,[" );

                    String [] strArr = myResponse.split(",,");
                    String [][] flagsArr = new String[4][3];
                    for(int i = 0; i < strArr.length; i++) {
                        String tempStr = strArr[i].substring(1, strArr[i].length() - 1).replaceAll("\\\"", "");
                        String [] tempArr = tempStr.split(",");
                        flagsArr[i] = tempArr;
                    }

                    List<Integer> numbers = new ArrayList<>();

                    for (int i = 0; i < 4; i++) {
                        numbers.add(i);
                    }
                    Collections.shuffle(numbers);

                    GameActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Set the main image
                            Log.d("Myresp", Arrays.deepToString(flagsArr));
                            Log.d("Myresp", String.valueOf(flagsArr.length));
                            Log.d("Myresp", String.valueOf(questionCount));
                            for(int i = 0; i < flagsArr.length; i++) {
                                Log.d("Myresp", "Running ?");
                                Log.d("Myresp", flagsArr[i][0].getClass().getName());
                                if(flagsArr[i][0].equals(questionCount + "")) {
                                    imgName = flagsArr[i][2];
                                    Log.i("Img", "Found!!!");
                                }
                            }
                            Log.i("Img", imgName);
                            imageViewFlag.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));

                            buttonA.setText(flagsArr[numbers.get(0)][1]);
                            buttonB.setText(flagsArr[numbers.get(1)][1]);
                            buttonC.setText(flagsArr[numbers.get(2)][1]);
                            buttonD.setText(flagsArr[numbers.get(3)][1]);
                        }
                    });
                }
            }
        });
    };
}