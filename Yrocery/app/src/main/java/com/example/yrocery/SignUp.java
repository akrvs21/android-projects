package com.example.yrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yrocery.POJO.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    private EditText editName, editPhone, editPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://yrocery-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Please wait");
                progressDialog.show();

                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                String password = editPassword.getText().toString();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(phone).exists()) {
                            progressDialog.dismiss();
//                            Toast.makeText(SignUp.this, "This user already exist", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            User user = new User(name, password);
                            table_user.child(phone).setValue(user);
                            Toast.makeText(SignUp.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
