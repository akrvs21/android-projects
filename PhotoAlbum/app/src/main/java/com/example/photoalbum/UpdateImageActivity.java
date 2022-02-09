package com.example.photoalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdateImageActivity extends AppCompatActivity {
    private ImageView imageUpdate;
    private EditText updateTitle, updateDesc;
    private Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image);

        imageUpdate = findViewById(R.id.imageViewUpdateImage);
        updateTitle = findViewById(R.id.updateTitle);
        updateDesc = findViewById(R.id.updateDescription);
        btnUpdate = findViewById(R.id.btnUpdate);

        imageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}