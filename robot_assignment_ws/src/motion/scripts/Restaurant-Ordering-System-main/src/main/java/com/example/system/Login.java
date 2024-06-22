package com.example.system;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText etEmail, etPassword;
    Button LoginBtn;
    Button RegisterBtn;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String Email, Password;
    Boolean CheckEditText;
    private String URL = "https://lamp.ms.wits.ac.za/~s2341162/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.editEmail);
        etPassword = findViewById(R.id.editPassword);
        LoginBtn = findViewById(R.id.stafflog);
        RegisterBtn = findViewById(R.id.RegisterBtn);
        Email = Password ="";
        requestQueue = Volley.newRequestQueue(Login.this);

        progressDialog = new ProgressDialog(Login.this);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    UserLogin();

                } else {

                    Toast.makeText(Login.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    // Creating user login function.
    public void UserLogin() {

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Matching server responce message to our text.
                        if(ServerResponse.equalsIgnoreCase("success")) {

                            // If response matched then show the toast.
                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_LONG).show();

                            // Finish the current Login activity.
                            finish();

                            // Opening the user profile activity using intent.
                            Intent intent = new Intent(Login.this, success.class);

                            // Sending User Email to another activity using intent.
                            intent.putExtra("UserEmailTAG", Email);

                            startActivity(intent);

                        }
                        else {

                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(Login.this, "Incorrect Email or Password", Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Login.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("CUSTOMER_EMAIL", Email);
                params.put("CUSTOMER_PASSWORD", Password);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    public void CheckEditTextIsEmptyOrNot() {

        // Getting values from EditText.
        Email = etEmail.getText().toString().trim();
        Password = etPassword.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }


    public void RGBH(View view) {
        Intent intent = new Intent(Login.this, REGISTER.class);


        startActivity(intent);
    }
}