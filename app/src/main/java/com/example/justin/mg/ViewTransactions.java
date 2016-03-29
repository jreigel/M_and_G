package com.example.justin.mg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewTransactions extends UserPanelScreen {
    private String [][] fillerData= {{"Business", "Amount", "Decision"}, {"Subway", "$7.00"}, {"Starbucks", "$8.00"}, {"Chipotle", "$5.50"}  };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_view_transactions);

        if(getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TableLayout tableLayout = new TableLayout(getApplicationContext());
        tableLayout.setVerticalScrollBarEnabled(true);

        List<List<String>> transactionDetails = new ArrayList<>();



        TableRow tableRow;
        int textSize, typeface;
        String [] headerData= {"Business", "Amount", "Decision"};

        if (extras != null) {
            student_id = (int) extras.get("id");
            helper = new DBhandler(this);
            populateText();

            transactionDetails = helper.getTransactions(student_id);
        }


        RelativeLayout.LayoutParams tableParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout rl=(RelativeLayout)findViewById(R.id.transactionLayout);

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
            textSize = 25;
            typeface = Typeface.BOLD;
            columnData.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
            columnData.setTypeface(null, typeface);
            tableRow.addView(columnData);
        }
        tableLayout.addView(tableRow);

//        transactions
        for(int i = transactionDetails.size() - 1; i >=0 ; i--)
        {
            tableRow = new TableRow(getApplicationContext());
            int tableRowSize = transactionDetails.get(i).size();
            int tableRowId = Integer.parseInt(transactionDetails.get(i).get(tableRowSize - 1));
            tableRow.setId(tableRowId);
//            -1 becaues the Last element is the row id.
            for(int j = 0; j< tableRowSize-1; j++)
            {
                TextView columnData = new TextView(getApplicationContext());
//                if it is the balance column or not
//                Todo: refactor this, too tightly coupled.
                if(j == 1) {
                    DecimalFormat df = new DecimalFormat("#0.00");
                    columnData.setText("$" + df.format(Float.parseFloat(transactionDetails.get(i).get(j))));
                }
                else{
                    columnData.setText(transactionDetails.get(i).get(j));
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
            RelativeLayout r2 = new RelativeLayout(getApplicationContext());
            TableRow.LayoutParams layout_description = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT);

            r2.setLayoutParams(layout_description);
            r2.setBackground(ContextCompat.getDrawable(this, R.drawable.table_border));


            final TextView acceptButton = new TextView(getApplicationContext());
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

            final TextView rejectButton = new TextView(getApplicationContext());
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

//            accept/reject handlers
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptConfirmation(acceptButton);
                }
            });
            rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rejectConfirmation(rejectButton);
                }
            });
            tableRow.addView(r2);
            tableLayout.addView(tableRow);
        }
        rl.requestLayout();
    }

    // todo: Refactor! Confirmations can be merged and cleaned. Confirmations in abstract class that takes msg and accept/reject as parameters?

    private void acceptConfirmation(final TextView acceptButton){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TableRow row = (TableRow) acceptButton.getParent().getParent();
        TextView businessView = (TextView) row.getChildAt(0);
        TextView amountView = (TextView) row.getChildAt(1);
        final String business = businessView.getText().toString();
        final Float amount = Float.parseFloat(amountView.getText().toString().replace("$", ""));

        builder.setMessage("By clicking confirm, you agree that you have shown this message to " + business + " and they have accepted your transaction of $" + amount)
                .setPositiveButton("Confirm.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TableRow row = (TableRow) acceptButton.getParent().getParent();
                        ViewGroup table = (ViewGroup) row.getParent();

                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                        Purchase p = new Purchase(business, date, amount, student_id);
                        if (helper.deductBalance(student_id, amount)) {
                            helper.insertPurchase(p);
                            purchaseNotification();
                            table.removeView(row);
                            helper.removeTransaction(row.getId());
                        }
                        else{
                            purchaseFailureNotification();
                        }
                    }
                })
                .setNegativeButton("Cancel.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void purchaseNotification(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The purchase has been successfully submitted!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        populateText();
    }
    private void purchaseFailureNotification(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The purchase was unsuccessful. Please make sure that your balance is sufficient before completing this purchase.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        populateText();
    }

    private void rejectConfirmation(final TextView rejectButton){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to reject this offer?")
                .setPositiveButton("Yes.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TableRow row = (TableRow) rejectButton.getParent().getParent();
                        ViewGroup table = (ViewGroup) row.getParent();
////                        get the text elements within the row (all elements to the left of decision column)
//                        for (int i = 0; i < row.getChildCount() - 1; i++) {
//                            TextView view = (TextView) row.getChildAt(i);
//                            System.out.println(view.getText());
//                        }
                        table.removeView(row);
                        helper.removeTransaction(row.getId());
                        rejectedNotification();
                    }
                })
                .setNegativeButton("No.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void rejectedNotification(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The transaction has been rejected.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
//        update the user panel
        populateText();
    }
}
