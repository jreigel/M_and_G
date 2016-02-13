package com.example.justin.mg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MakeTransaction extends AppCompatActivity {


    private DBhandler helper = new DBhandler(this);
    private int student_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_transaction);

        Bundle extras = getIntent().getExtras();

        Purchase p;


        if (extras != null) {
            student_id = (int) extras.get("id");
        }

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button purchases = (Button) findViewById(R.id.TransactionSubmit);
        purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmation();
            }
        });
    }
    private void confirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to make this transaction?")
                .setPositiveButton("Yes.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText businessView = (EditText) findViewById((R.id.BusinessInput));
                        EditText amountView = (EditText) findViewById((R.id.AmountInput));
                        String business = businessView.getText().toString();
                        float amount = 0;
                        try {
                            amount = Float.parseFloat(amountView.getText().toString());
                            amount = round(amount, 2);
                            System.out.println(amount);
                            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                            Purchase p = new Purchase(business, date, amount, student_id);
                            helper.insertPurchase(p);
                            helper.deductBalance(student_id, amount);
                            notification();
                        } catch (NumberFormatException e) {
                            System.out.println("Number Format exception");
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton("No.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    private void notification(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The transaction has been successfully submitted!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
