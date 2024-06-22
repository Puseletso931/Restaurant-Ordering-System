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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class STAFFREGISTER extends AppCompatActivity {
    EditText etEmail;
    EditText etPassword;
    EditText etfame;
    EditText etlame;
    EditText etRestaurant;
    Button LoginBtn;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String STAFF_FNAME,STAFF_LNAME,STAFF_LOGIN,STAFF_PASSWORD,RESTAURANT_ID;
    Boolean CheckEditText;
    private String URL = "https://lamp.ms.wits.ac.za/~s2341162/staffregister.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffregister);

        etfame = findViewById(R.id.stafffirsname);
        etlame = findViewById(R.id.stafflastname);
        etEmail = findViewById(R.id.staffEmail);
        etPassword = findViewById(R.id.verifypassword);
        etRestaurant = findViewById(R.id.Restaurant_id);
        LoginBtn=(findViewById(R.id.REGISTER));
        STAFF_FNAME=STAFF_LNAME=STAFF_LOGIN=STAFF_PASSWORD=RESTAURANT_ID= "";
        requestQueue = Volley.newRequestQueue(STAFFREGISTER.this);

        progressDialog = new ProgressDialog(STAFFREGISTER.this);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    UserLogin();

                } else {

                    Toast.makeText(STAFFREGISTER.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    public void CheckEditTextIsEmptyOrNot() {

        STAFF_FNAME=etfame.getText().toString().trim();
        STAFF_LNAME=etlame.getText().toString().trim();
        STAFF_LOGIN= etEmail.getText().toString().trim();
        STAFF_PASSWORD= etPassword.getText().toString().trim();
        RESTAURANT_ID=etRestaurant.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty( STAFF_FNAME) || TextUtils.isEmpty( STAFF_LNAME) || TextUtils.isEmpty(STAFF_LOGIN) || TextUtils.isEmpty( STAFF_PASSWORD) ||  TextUtils.isEmpty(RESTAURANT_ID)) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
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
                    public void onResponse(String response) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Matching server responce message to our text.
                        if (response.equals("success")){

                            // If response matched then show the toast.
                            Toast.makeText(STAFFREGISTER.this, "Registered Successfully", Toast.LENGTH_LONG).show();

                            // Finish the current Login activity.
                            finish();

                            // Opening the user profile activity using intent.
                              Intent intent = new Intent(STAFFREGISTER.this, STAFFLOGIN.class);


                              startActivity(intent);

                        }
                        else {

                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(STAFFREGISTER.this,"Try Again", Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(STAFFREGISTER.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("STAFF_LNAME", STAFF_LNAME);
                params.put("STAFF_FNAME",STAFF_FNAME);
                params.put("STAFF_LOGIN",STAFF_LOGIN);
                params.put("STAFF_PASSWORD", STAFF_PASSWORD);
                params.put("RESTAURANT_ID", RESTAURANT_ID);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(STAFFREGISTER.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    public void lgnclick(View view) {
        Intent intent = new Intent(STAFFREGISTER.this, STAFFLOGIN.class);
        startActivity(intent);
    }
}