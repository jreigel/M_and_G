package com.example.justin.mg;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewPurchases extends AppCompatActivity {
    private DBhandler helper = new DBhandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_view_purchases);

        if(getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TableLayout tableLayout = new TableLayout(getApplicationContext());
        tableLayout.setVerticalScrollBarEnabled(true);

        int id;
        List<List<String>> productDetails = new ArrayList<>();



        TableRow tableRow;
        int textSize, typeface;
        String [] headerData= {"Business", "Amount", "Date(Y-M-D)"};

        if (extras != null) {
            id = (int) extras.get("id");
            productDetails = helper.getPurchases(id);
//            todo: fragment these to keep textviews on all screens.
//            todo: move code to a different function.
            System.out.println(productDetails);
        }

//        TODO: Refactor and update the Scroll View so it works with rotate and doesnt use magic numbers.

        RelativeLayout.LayoutParams tableParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                1000
        );


        RelativeLayout rl=(RelativeLayout)findViewById(R.id.purchaseLayout);

        tableParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        ScrollView sv = new ScrollView(this);

        sv.addView(tableLayout);
        rl.addView(sv, tableParams);

//        header
        tableRow = new TableRow(getApplicationContext());
        for(String header: headerData){
//            TODO: refactor column data to prevent duplicated code and visibility issues (layoutparams?)
            TextView columnData = new TextView(getApplicationContext());
            columnData.setTextColor(Color.BLACK);
            columnData.setText(header);
            columnData.setPadding(20, 20, 20, 20);
            columnData.setBackground(ContextCompat.getDrawable(this, R.drawable.table_border));
            textSize = 23;
            typeface = Typeface.BOLD;
            columnData.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
            columnData.setTypeface(null, typeface);
            tableRow.addView(columnData);
        }
        tableLayout.addView(tableRow);
        for(int i = 0; i < productDetails.size(); i++)
        {
            tableRow = new TableRow(getApplicationContext());
            for(int j = 0; j< productDetails.get(i).size(); j++)
            {
                TextView columnData = new TextView(getApplicationContext());
//                if it is the balance column or not
//                Todo: refactor this, too tightly coupled.
                if(j == 1) {
                    DecimalFormat df = new DecimalFormat("#0.00");
                    columnData.setText("$" + df.format(Float.parseFloat(productDetails.get(i).get(j))));
                }
                else{
                    columnData.setText(productDetails.get(i).get(j));
                }
                columnData.setTextColor(Color.BLACK);
                columnData.setPadding(20, 20, 20, 20);
                columnData.setBackground(ContextCompat.getDrawable(this, R.drawable.table_border));
                textSize = 21;
                typeface = Typeface.NORMAL;
                columnData.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                columnData.setTypeface(null, typeface);
                tableRow.addView(columnData);
            }
            tableLayout.addView(tableRow);
        }
        rl.requestLayout();

    }
}
