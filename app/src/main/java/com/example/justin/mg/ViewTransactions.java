package com.example.justin.mg;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class ViewTransactions extends AppCompatActivity {
    private String [][] fillerData= {{"Name", "Amount", "Decision"}, {"Jane", "$7.00"}, {"Steve", "$8.00"}, {"Sam", "$5.50"}  };
    private DBhandler helper = new DBhandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transactions);
        if(getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TableRow tableRow;
        int textSize, typeface;
        Bundle extras = getIntent().getExtras();

        RelativeLayout rl=(RelativeLayout)findViewById(R.id.transactionLayout);

        int id;
        List<List<String>> productDetails;


        if (extras != null) {
            id = (int) extras.get("id");
            productDetails = helper.getPurchases(id);
//            todo: fragment these to keep textviews on all screens.
//            todo: move code to a different function.
            System.out.println(productDetails);
        }

        TableLayout tableLayout = new TableLayout(getApplicationContext());
        RelativeLayout.LayoutParams tableParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        tableLayout.setVerticalScrollBarEnabled(true);

        tableParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

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
                //            name.s(ContextCompat.getColor(this, R.style.MyActionBar));
                textSize = (i == 0) ? 25 : 21;
                typeface = (i == 0) ? Typeface.BOLD : Typeface.NORMAL;
                columnData.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                columnData.setTypeface(null, typeface);
                tableRow.addView(columnData);
            }
            if(i > 0) {
                RelativeLayout r2 = new RelativeLayout(getApplicationContext());
                TableRow.LayoutParams layout_description = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT);

                r2.setLayoutParams(layout_description);
                r2.setBackground(ContextCompat.getDrawable(this, R.drawable.table_border));


                TextView acceptButton = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );

                params.width = 150;//change the width size
                params.height = 80;
                params.setMargins(10, 0, 0, 0);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, r2.getId());
//                params.
                acceptButton.setLayoutParams(params);
                acceptButton.setText(R.string.accept_button);
                acceptButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                acceptButton.setTextColor(Color.BLACK);
                acceptButton.setTypeface(null, Typeface.NORMAL);


                acceptButton.setBackgroundColor(Color.parseColor("#11ff00"));
                r2.addView(acceptButton);

                TextView rejectButton = new TextView(getApplicationContext());
                rejectButton.setText(R.string.reject_button);
                rejectButton.setBackgroundColor(Color.parseColor("#ff0000"));

                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );

                params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, r2.getId());
                rejectButton.setLayoutParams(params2);
                params2.width = 150;
                params2.height = 80;
                params2.setMargins(0, 0, 10, 0);
                params2.addRule(RelativeLayout.CENTER_VERTICAL);
                rejectButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                rejectButton.setTextColor(Color.BLACK);
                rejectButton.setTypeface(null, Typeface.NORMAL);
                r2.addView(rejectButton);
                tableRow.addView(r2);
            }
            tableLayout.addView(tableRow);
//            c.moveToNext() ;
        }
    }
}
