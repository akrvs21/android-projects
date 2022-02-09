package com.example.photoalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddImageActivity extends AppCompatActivity {
    private ImageView imageViewAddImage;
    private EditText editTextDesc, editTextAddTitle;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Image");
        setContentView(R.layout.activity_add_image);

        imageViewAddImage = findViewById(R.id.imageViewUpdateImage);
        editTextDesc = findViewById(R.id.updateDescription);
        editTextAddTitle = findViewById(R.id.updateTitle);
        btnSave = findViewById(R.id.btnUpdate);

        imageViewAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}