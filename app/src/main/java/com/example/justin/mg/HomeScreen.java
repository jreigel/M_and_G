package com.example.justin.mg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeScreen extends AppCompatActivity {

    private DBhandler helper = new DBhandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_home_screen);

        int id;
        String[] userDetails;

        if (extras != null) {
            id = (int) extras.get("id");
            userDetails = helper.userDetails(id);
//            todo: fragment these to keep textviews on all screens.
//            todo: move code to a different function.
            System.out.println(userDetails[0]);
            String firstName = userDetails[0];
            String lastName = userDetails[1];
            String balance = userDetails[2];
            TextView nameView = (TextView) findViewById(R.id.FullName);
            TextView idView = (TextView) findViewById(R.id.Asurite);
            TextView balanceView = (TextView) findViewById(R.id.MandG);
            nameView.setText(firstName + " " + lastName);
            idView.setText("ASURITE: " + id);
            balanceView.setText("Balance: $" + balance);
            System.out.println("Working");
        }


        Button transaction = (Button) findViewById(R.id.transactionNav);
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeScreen.this, MakeTransaction.class);
//                TODO: Pass in any needed data.
//                myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
            }
        });
        Button purchases = (Button) findViewById(R.id.purchaseNav);
        purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeScreen.this, ViewPurchases.class);
//                TODO: Pass in any needed data.
//                myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
            }
        });
        Button offers = (Button) findViewById(R.id.offersNav);
        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeScreen.this, ViewTransactions.class);
//                TODO: Pass in any needed data.
//                myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
            }
        });
    }
}
