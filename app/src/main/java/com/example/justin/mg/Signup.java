package com.example.justin.mg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Justin on 1/25/2016.
 */
public class Signup extends Activity{
    private final DBhandler dbHelper = new DBhandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        Button signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outer_loop:
                {
                    EditText firstName = (EditText) findViewById(R.id.firstNameSignup);
                    EditText lastName = (EditText) findViewById(R.id.lastNameSignup);
                    EditText asurite = (EditText) findViewById(R.id.asuriteSignup);
                    EditText email = (EditText) findViewById((R.id.emailSignup));
                    EditText password = (EditText) findViewById(R.id.passwordSignup);
                    EditText password2 = (EditText) findViewById(R.id.passwordSignup2);

//                    todo: clean up code by merging exceptions and other methods, rename asurite to id.

                    if (hasEmpty(new EditText[]{firstName, lastName, asurite, password, password2}))
                        break outer_loop;

                    String firstNameText = firstName.getText().toString();
                    String lastNameText = lastName.getText().toString();
                    int asuriteText = 0;

                    //                Asurite initialization/validation
                    try {
                        asuriteText = Integer.parseInt(asurite.getText().toString());
                        if (asurite.getText().toString().length() != 10) {
                            Toast asuriteLengthException = Toast.makeText(Signup.this, "Invalid asurite. A valid asurite id contains exactly 10 digits.", Toast.LENGTH_SHORT);
                            asuriteLengthException.show();
                            break outer_loop;
                        }
                    } catch (NumberFormatException e) {
                        //                    This message is only if the field is not empty and contains a non-number.
                        if (!asurite.getText().toString().isEmpty()) {
                            Toast numberException = Toast.makeText(Signup.this, "Invalid asurite. An asurite consists only of numbers.", Toast.LENGTH_SHORT);
                            e.printStackTrace();
                            numberException.show();
                            break outer_loop;
                        }
                    }
                    String emailText = email.getText().toString();
                    String passwordText = password.getText().toString();
                    String passwordText2 = password2.getText().toString();

//                    Exception handling with final verification at the end.
                    if (passwordText.length() < 5 || passwordText2.length() < 5){
                        displayError("Passwords must be at least 5 characters long.");
                    } else if (!passwordText.equals(passwordText2)) {
                        displayError("Passwords do not match.");
                    } else if(dbHelper.getUserId(emailText) != 0){
                        displayError("This email already exists.", "An account already exists with this email address. " +
                                "Please use a different email.",email);
                    } else if(dbHelper.getUserEmail(asuriteText) != null){
                        displayError("This id already exists.", "An account already exists with this id", asurite);
                    }
                    else {
                        //                    Insert db details
                        int balance = (int) Math.ceil(Math.random() * 200) + 400;
                        User u = new User(asuriteText, balance, firstNameText, lastNameText, emailText, passwordText);
                        dbHelper.insertUser(u);
                        alertMessage();
                    }
                }
            }

        });
    }
    private void displayError(String mainMessage, String fieldMessage, EditText textField){
        Toast pass = Toast.makeText(Signup.this, mainMessage, Toast.LENGTH_SHORT);
        pass.show();
        textField.setError(fieldMessage);
    }

    private void displayError(String mainMessage) {
        Toast pass = Toast.makeText(Signup.this, mainMessage, Toast.LENGTH_SHORT);
        pass.show();
    }

    private boolean hasEmpty(EditText[] fields){
        for(EditText field : fields){
            if(TextUtils.isEmpty(field.getText().toString())) {
                field.setError("This field cannot be empty. Please make sure to fill in all other fields as well.");
                return true;
            }
        }
        return false;
    }
    private void alertMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Account created successfully!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent myIntent = new Intent(Signup.this, LoginActivity.class);
                        startActivity(myIntent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



}
