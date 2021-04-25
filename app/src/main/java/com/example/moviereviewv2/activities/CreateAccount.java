package com.example.moviereviewv2.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviereviewv2.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class CreateAccount extends AppCompatActivity {
    MovieReviewDatabaseHelper DB;

    EditText firstNameEdtTxt, lastNameEdtText, emailEdtTxt, passwordEdtTxt, passwordRetpeEdtText;
    Button btnSignup;

    TextInputLayout til_genders;
    AutoCompleteTextView act_genders;
    TextInputLayout til_countries;
    AutoCompleteTextView act_countries;

    ArrayList<String> arrayList_genders;
    ArrayAdapter<String> arrayAdapter_genders;
    ArrayList<String> arrayList_countries;
    ArrayAdapter<String> arrayAdapter_countries;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        //DB
        DB = new MovieReviewDatabaseHelper(this);
        //Text Fields
        firstNameEdtTxt = findViewById(R.id.firstNameEdtTxt);
        lastNameEdtText = findViewById(R.id.lastNameEdtText);
        emailEdtTxt = findViewById(R.id.emailEdtTxt);
        passwordEdtTxt = findViewById(R.id.passwordEdtTxt);
        passwordRetpeEdtText = findViewById(R.id.passwordRetpeEdtText);
        //Buttons
        btnSignup = findViewById(R.id.btnSignup);
        //Genders Dropdown List
        til_genders = (TextInputLayout) findViewById(R.id.til_genders);
        act_genders = (AutoCompleteTextView) findViewById(R.id.act_genders);
        arrayList_genders = new ArrayList<>();
        arrayList_genders.add("Male");
        arrayList_genders.add("Female");
        arrayList_genders.add("Other");
        arrayAdapter_genders = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList_genders);
        act_genders.setAdapter(arrayAdapter_genders);
        act_genders.setThreshold(1);

        //Countries Dropdown List
        til_countries = (TextInputLayout) findViewById(R.id.til_countries);
        act_countries = (AutoCompleteTextView) findViewById(R.id.act_countries);
        arrayList_countries = new ArrayList<>();
        arrayList_countries.add("Australia");
        arrayList_countries.add("Canada");
        arrayList_countries.add("Netherlands");
        arrayList_countries.add("United States");
        arrayList_countries.add("United Kingdom");
        arrayList_countries.add("Suriname");
        arrayAdapter_countries = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList_countries);
        act_countries.setAdapter(arrayAdapter_countries);
        act_countries.setThreshold(1);

    }
        //Create Account
        public void onClickSignup (View view) {
            String firstName = firstNameEdtTxt.getText().toString();
            String lastName = lastNameEdtText.getText().toString();
            String email = emailEdtTxt.getText().toString();
            String gender = act_genders.getText().toString();
            String country = act_countries.getText().toString();

            Boolean checkInsertData = DB.insertNewMember(firstName, lastName, gender, email,/*password, passwordRetpe,*/ country);
            if (checkInsertData) {
                Toast.makeText(CreateAccount.this, "Account created", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CreateAccount.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        //Login
        public void onClickLoginScreen (View view){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        //Delete Account
        public void onClickDeleteData (View view){
            String firstname = firstNameEdtTxt.getText().toString();

            Boolean checkDeleteData = DB.deleteMember(firstname);
            if (checkDeleteData) {
                Toast.makeText(CreateAccount.this, "Account deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CreateAccount.this, "Could not delete", Toast.LENGTH_SHORT).show();
            }
        }
        //Update Account
        public void onClickUpdateData (View view){
//            String firstname = firstNameEdtTxt.getText().toString();
//            String email = emailEdtTxt.getText().toString();
//
//            Boolean checkUpdateData = DB.updateMember(firstname, email);
//            if (checkUpdateData) {
//                Toast.makeText(CreateAccount.this, "Emailadres updated", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(CreateAccount.this, "Could not update", Toast.LENGTH_SHORT).show();
//            }
                new updateMemberAsync().execute();
        }
        //View Account Data
        public void onClickViewData (View view){
            Cursor result = DB.getData();
            if (result.getCount() == 0) {
                Toast.makeText(CreateAccount.this, "No data", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()) {
                buffer.append("First Name: " + result.getString(1) + "\n");
                buffer.append("Last Name: " + result.getString(2) + "\n");
                buffer.append("Gender: " + result.getString(3) + "\n");
                buffer.append("Email: " + result.getString(4) + "\n");
                buffer.append("Country: " + result.getString(5) + "\n");
                buffer.append("Register Date: " + result.getString(6) + "\n\n");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
            builder.setCancelable(true);
            builder.setTitle("Members");
            builder.setMessage(buffer.toString());
            builder.show();
        }

    private class updateMemberAsync  extends AsyncTask< String, Void, Boolean> {
        Boolean checkUpdateData;
        EditText FIRST_NAME = (EditText) findViewById(R.id.firstNameEdtTxt);
        EditText EMAIL = (EditText) findViewById(R.id.emailEdtTxt);
        @Override
        protected Boolean doInBackground(String ... user) {
            String firstnametxt = FIRST_NAME.getText().toString();
            String emailtxt = EMAIL.getText().toString();
            checkUpdateData = DB.updateMember(firstnametxt, emailtxt);
            return checkUpdateData;
        }
        @Override
        protected void onPostExecute(Boolean checkUpdateData) {
            if (checkUpdateData ==  true) {
                Toast toast = Toast.makeText(CreateAccount.this, "Database updated", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(CreateAccount.this, "Database not updated", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    }
