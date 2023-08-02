package com.example.system;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import com.android.volley.RequestQueue;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class REGISTER extends AppCompatActivity {
    EditText etfame, etlame, etEmail, etPassword, etPhone, etReenterPassword;
    String FName, LName, Email, Password, Phone, reenterPassword;
    Button RegisterBtn;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Boolean  CheckEditText;
    private String URL = "https://lamp.ms.wits.ac.za/~s2341162/prop.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etfame = findViewById(R.id.firsname);
        etlame = findViewById(R.id.lastname);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.editPassword);
        etPhone = findViewById(R.id.editTextPhone);
        etReenterPassword = findViewById(R.id.etReenterPassword);
        RegisterBtn = findViewById(R.id.RegisterBtn);
        FName = LName = Email = Password = Phone = "";
        requestQueue = Volley.newRequestQueue(REGISTER.this);

        progressDialog = new ProgressDialog(REGISTER.this);

        reenterPassword = etReenterPassword.getText().toString().trim();


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    UserRegistration();

                }
                else {

                    Toast.makeText(REGISTER.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }


    public void CheckEditTextIsEmptyOrNot(){

        FName = etfame.getText().toString().trim();
        LName = etlame.getText().toString().trim();
        Phone = etPhone.getText().toString().trim();
        Email = etEmail.getText().toString().trim();
        Password = etPassword.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if(TextUtils.isEmpty(FName) || TextUtils.isEmpty(LName) || TextUtils.isEmpty(Phone)|| TextUtils.isEmpty(Password))
        {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        }
        else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true ;
        }
    }



    public void UserRegistration(){
                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.equals("success")) {
                            Toast.makeText(REGISTER.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(REGISTER.this, Login.class);
                            intent.putExtra("UserEmailTAG", FName);
                            startActivity(intent);
                        } else if (response.equals("failure")) {
                            Toast.makeText(REGISTER.this, "Try Again", Toast.LENGTH_LONG).show();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(REGISTER.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<>();
                        data.put("Lname", LName);
                        data.put("Fname", FName);
                        data.put("Phone", Phone);
                        data.put("Email", Email);
                        data.put("Password", Password);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }

}