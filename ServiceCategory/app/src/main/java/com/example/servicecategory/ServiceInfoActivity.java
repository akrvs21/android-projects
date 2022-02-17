package com.example.servicecategory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ServiceInfoActivity extends AppCompatActivity {
    private TextView titleView;
    private TextView minPayText;
    private TextView maxPayText;
    private Button btnback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);
         titleView = findViewById(R.id.serviceTitle);
         minPayText = findViewById(R.id.minpay);
         maxPayText = findViewById(R.id.maxpay);
         btnback = findViewById(R.id.btnBack);

        Intent intent= getIntent();
        String paymin = intent.getStringExtra("paymin");
        String paymax = intent.getStringExtra("paymax");
        String title = intent.getStringExtra("title");

        titleView.setText(title);
        minPayText.setText("Minimum pay: " + paymin);
        maxPayText.setText("Maximum pay: " + paymax);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceInfoActivity.this.finish();
            }
        });

    }
}