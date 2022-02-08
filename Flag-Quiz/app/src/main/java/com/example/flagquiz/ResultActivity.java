package com.example.flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView totalCorrect, totalWrong, successRate;
    private Button btnQuit, btnPlay;
    private int correct, wrong, rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        totalCorrect = findViewById(R.id.TotalCorrect);
        totalWrong = findViewById(R.id.TotalWrong);
        btnPlay = findViewById(R.id.btnPlay);
        btnQuit = findViewById(R.id.btnQuit);
        successRate = findViewById(R.id.successRate);

        correct = getIntent().getExtras().getInt("correct");
        wrong = getIntent().getExtras().getInt("wrong");
        rate = getIntent().getExtras().getInt("rate");

        totalCorrect.setText("Total correct: " + correct);
        totalWrong.setText("Total wrong: " + wrong);
        successRate.setText("Success rate: " + rate + "%");

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}