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

public class ViewPurchases extends AppCompatActivity {
    private String [][] fillerData= {{"Business", "Amount", "Date"}, {"Subway", "$6.17", "12/15/15"}, {"Starbucks", "$8.32", "12/20/15"}, {"Chik-fil-a", "$10.50", "12/23/15"} };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_purchases);
        if(getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TableLayout tableLayout = new TableLayout(getApplicationContext());
        tableLayout.setVerticalScrollBarEnabled(true);
        TableRow tableRow;
        int textSize, typeface;

        RelativeLayout.LayoutParams tableParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        tableParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        RelativeLayout rl=(RelativeLayout)findViewById(R.id.purchaseLayout);
        ScrollView sv = new ScrollView(this);

        sv.addView(tableLayout);
        rl.addView(sv, tableParams);
        for(int i = 0; i < fillerData.length; i++)
        {
            tableRow = new TableRow(getApplicationContext());
            for(int j = 0; j< fillerData[i].length; j++)
            {
                TextView columnData = new TextView(getApplicationContext());
                columnData.setText(fillerData[i][j]);
                columnData.setTextColor(Color.BLACK);
                columnData.setPadding(20, 20, 20, 20);
                columnData.setBackground(ContextCompat.getDrawable(this, R.drawable.table_border));
                textSize = (i == 0) ? 25 : 21;
                typeface = (i == 0) ? Typeface.BOLD : Typeface.NORMAL;
                columnData.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                columnData.setTypeface(null, typeface);
                tableRow.addView(columnData);
            }
            tableLayout.addView(tableRow);
        }
        rl.requestLayout();

    }
}
