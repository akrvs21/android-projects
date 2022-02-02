package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnPlus,btnMinus,btnPlusMinus,btnMultiply,btnDivide,btnPerc,btnEqual,btnDel, btnDot;
    private TextView textViewResult, textViewHistory;

    private  String number = null;
    double firstNumber = 0;
    double lastNumber = 0;

    String status = null;
    boolean operator = false;

    DecimalFormat myFormatter = new DecimalFormat("######.######");

    String history, currentResult;

    boolean dot = true;
    boolean btnEqualsControl = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.num0);
        btn1 = findViewById(R.id.num1);
        btn2 = findViewById(R.id.num2);
        btn3 = findViewById(R.id.num3);
        btn4 = findViewById(R.id.num4);
        btn5 = findViewById(R.id.num5);
        btn6 = findViewById(R.id.num6);
        btn7 = findViewById(R.id.num7);
        btn8 = findViewById(R.id.num8);
        btn9 = findViewById(R.id.num9);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiply = findViewById(R.id.btnMult);
        btnDivide = findViewById(R.id.btnDivide);
        btnPlusMinus = findViewById(R.id.btnPlusMinus);
        btnPerc = findViewById(R.id.btnPercent);
        btnEqual = findViewById(R.id.btnEqual);
        btnDot = findViewById(R.id.btnDot);
        btnDel = findViewById(R.id.btnDel);
        textViewResult = findViewById(R.id.textViewResult);
        textViewHistory = findViewById(R.id.textViewHistory);



        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.num0:
                        numberClick("0");
                        break;
                    case R.id.num1:
                        numberClick("1");
                        break;
                    case R.id.num2:
                        numberClick("2");
                        break;
                    case R.id.num3:
                        numberClick("3");
                        break;
                    case R.id.num4:
                        numberClick("4");
                        break;
                    case R.id.num5:
                        numberClick("5");
                        break;
                    case R.id.num6:
                        numberClick("6");
                        break;
                    case R.id.num7:
                        numberClick("7");
                        break;
                    case R.id.num8:
                        numberClick("8");
                        break;
                    case R.id.num9:
                        numberClick("9");
                        break;
                    case R.id.btnDel:
                        number = null;
                        status = null;
                        textViewResult.setText("0");
                        textViewHistory.setText("");
                        firstNumber = 0;
                        lastNumber = 0;
                        dot = true;
                        break;
                    case R.id.btnDivide:
                        history = textViewHistory.getText().toString();
                        currentResult = textViewResult.getText().toString();
                        textViewHistory.setText(history + currentResult + " / ");
                        if(operator) {
                            if(status == "subtraction") {
                                minus();
                            } else if(status == "multiplication") {
                                multiply();
                            } else if (status == "sum") {
                                plus();
                            } else {
                                divide();
                            }
                        }
                        status = "division";
                        operator = false;
                        number = null;
                        break;
                    case R.id.btnDot:
                        if(dot) {
                            if(number == null) {
                                number = "0.";
                            } else {
                                number = number + ".";
                            }
                        }

                        textViewResult.setText(number);
                        dot = false;
                        break;
                    case R.id.btnMinus:
                        history = textViewHistory.getText().toString();
                        currentResult = textViewResult.getText().toString();
                        textViewHistory.setText(history + currentResult + " - ");
                        if(operator) {
                            if(status == "multiplication") {
                                multiply();
                            } else if(status == "division") {
                                divide();
                            } else if (status == "sum") {
                                plus();
                            } else {
                                minus();
                            }
                        }
                        status = "subtraction";
                        operator = false;
                        number = null;
                        break;
                    case R.id.btnMult:
                        history = textViewHistory.getText().toString();
                        currentResult = textViewResult.getText().toString();
                        textViewHistory.setText(history + currentResult + " x ");
                        if(operator) {
                            if(status == "subtraction") {
                                minus();
                            } else if(status == "division") {
                                divide();
                            } else if (status == "sum") {
                                plus();
                            } else {
                                multiply();
                            }
                        }
                        status = "multiplication";
                        operator = false;
                        number = null;
                        break;
                    case R.id.btnPercent:

                        break;
                    case R.id.btnPlus:
                        history = textViewHistory.getText().toString();
                        currentResult = textViewResult.getText().toString();
                        textViewHistory.setText(history + currentResult + " + ");
                        if(operator) {
                            if(status == "multiplication") {
                                multiply();
                            } else if(status == "division") {
                                divide();
                            } else if (status == "subtraction") {
                                minus();
                            } else {
                                plus();
                            }
                        }
                        status = "sum";
                        operator = false;
                        number = null;
                        break;
                    case R.id.btnEqual:
                        if(operator) {
                            if(status == "sum") {
                                plus();
                            } else if(status == "division") {
                                divide();
                            } else if (status == "subtraction") {
                                minus();
                            } else if (status == "multiplication"){
                                multiply();
                            } else {
                                firstNumber = Double.parseDouble(textViewResult.getText().toString());
                            }
                        }
                        operator = false;
                        btnEqualsControl = true;
                    default:
                        break;
                }
            }
        };
        btn0.setOnClickListener(listener);
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);
        btn7.setOnClickListener(listener);
        btn8.setOnClickListener(listener);
        btn9.setOnClickListener(listener);
        btnPlus.setOnClickListener(listener);
        btnMinus.setOnClickListener(listener);
        btnMultiply.setOnClickListener(listener);
        btnDivide.setOnClickListener(listener);
        btnPlusMinus.setOnClickListener(listener);
        btnPerc.setOnClickListener(listener);
        btnEqual.setOnClickListener(listener);
        btnDot.setOnClickListener(listener);
        btnDel.setOnClickListener(listener);
        textViewResult.setOnClickListener(listener);
        textViewHistory.setOnClickListener(listener);
    }

    public void numberClick(String view) {
        if(number == null) {
            number = view;
        } else if(btnEqualsControl) {
            firstNumber = 0;
            lastNumber = 0;
            number = view;
        }
        else {
            number = number + view;
        }
        textViewResult.setText(number);
        operator = true;
        btnEqualsControl = false;
    }
    public void plus() {
        lastNumber = Double.parseDouble(textViewResult.getText().toString());
        firstNumber = firstNumber + lastNumber;
        textViewResult.setText(myFormatter.format(firstNumber));
        dot = true;
    }

    public void minus() {
        if(firstNumber == 0) {
            firstNumber = Double.parseDouble(textViewResult.getText().toString());
        } else {
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = firstNumber - lastNumber;
        }
        textViewResult.setText(myFormatter.format(firstNumber));
        dot = true;
    }

    public void multiply() {
        if(firstNumber == 0) {
            firstNumber = 1;
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = firstNumber * lastNumber;
        } else {
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = firstNumber * lastNumber;
        }
        textViewResult.setText(myFormatter.format(firstNumber));
        dot = true;
    }

    public void divide() {
        if(firstNumber == 0) {
            lastNumber =  Double.parseDouble(textViewResult.getText().toString());
            firstNumber = lastNumber / 1;
        } else {
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = firstNumber / lastNumber;
        }
        textViewResult.setText(myFormatter.format(firstNumber));
        dot = true;
    }
}