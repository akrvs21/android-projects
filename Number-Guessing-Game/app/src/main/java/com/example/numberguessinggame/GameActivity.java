package com.example.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView textViewLast, textViewChance, textViewHint;
    private EditText editTextGuess;
    private Button btnConfirm;

    boolean twoDigits, threeDigits, fourDigits;
    Random r = new Random();
    int random;
    int remainingChances = 10;
    ArrayList<Integer> guessesList = new ArrayList<>();
    int userAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textViewLast = findViewById(R.id.textViewLast);
        textViewChance = findViewById(R.id.textViewChance);
        textViewHint = findViewById(R.id.textViewHint);
        editTextGuess = findViewById(R.id.editTextNumber);
        btnConfirm = findViewById(R.id.btnConfirm);

        twoDigits = getIntent().getBooleanExtra("two", false);
        threeDigits = getIntent().getBooleanExtra("three", false);
        fourDigits = getIntent().getBooleanExtra("four", false);

        if(twoDigits) {
            random = r.nextInt(90) + 10;
        } else if(threeDigits) {
            random = r.nextInt(900) + 100;
        } else if(twoDigits) {
            random = r.nextInt(9000) + 1000;
        }
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = editTextGuess.getText().toString();
                if(guess.equals("")){
                    Toast.makeText(GameActivity.this, "Please enter some value", Toast.LENGTH_LONG).show();

                } else {
                    textViewLast.setVisibility(View.VISIBLE);
                    textViewChance.setVisibility(View.VISIBLE);
                    textViewHint.setVisibility(View.VISIBLE);

                    userAttempts++;
                    remainingChances--;

                    int userGuess = Integer.parseInt(guess);
                    guessesList.add(userGuess);

                    textViewLast.setText("Your last guessed #: " + guess);
                    textViewChance.setText("Your remaining chances: " + remainingChances);

                    if(random == userGuess) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Guess the Number Game");
                        builder.setCancelable(false);
                        builder.setMessage("Congrats. My guess was " + random + "\n\n You know my number in " + userAttempts + " attempts.\n\n Your" +
                                " guesses: " + guessesList + "\n\n Would you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    } else if (random < userGuess) {
                        textViewHint.setText("Decrease your guess");
                    } else if (random > userGuess) {
                        textViewHint.setText("Increase your guess");
                    }
                    if(remainingChances == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Guess the Number Game");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry, you didn't found my number. \n\n Your right to guess is over.\n\n My number was: " + random
                        + " Your guesses: " + guessesList + "\n\n Would you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                    editTextGuess.setText("");
                }
            }
        });
    }
}