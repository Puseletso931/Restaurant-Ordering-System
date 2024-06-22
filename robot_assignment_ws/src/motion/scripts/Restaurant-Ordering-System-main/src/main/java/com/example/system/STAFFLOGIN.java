package com.example.system;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

public class STAFFLOGIN extends AppCompatActivity {
    private EditText SLogEmail, SLogPassword;
    Button LoginBtn;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String Email, Password;
    Boolean CheckEditText=true;
    private String URL = "https://lamp.ms.wits.ac.za/~s2341162/mpho.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stafflogin);
        SLogEmail= findViewById(R.id.verifyemail);
        SLogPassword= findViewById(R.id.verifypassword);
        LoginBtn = findViewById(R.id.verifylogin);
        Email = Password ="";
        requestQueue = Volley.newRequestQueue(STAFFLOGIN.this);

        progressDialog = new ProgressDialog(STAFFLOGIN.this);
        //onclick listener for login button
        //checks validity of input
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoginInfo();
                if(CheckEditText){
                    UserLogin();
                }
                else{
                    Toast.makeText(STAFFLOGIN.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //check validity of email entered
    boolean isEmail(EditText text){
        CharSequence email=text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    //check if input is empty
    boolean isEmpty(EditText text){
        CharSequence str=text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    void checkLoginInfo() {
        Email = SLogEmail.getText().toString().trim();
        Password = SLogPassword.getText().toString().trim();
        if(isEmpty(SLogEmail)){
            SLogEmail.setError("Email is required!");
            CheckEditText=false;
        }
        if(!isEmpty(SLogEmail) && !isEmail(SLogEmail)){
            SLogEmail.setError("Enter valid Email!");
            CheckEditText=false;
        }
        if(isEmpty(SLogPassword)){
            SLogPassword.setError("Password is required!");
            CheckEditText=false;
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
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Matching server response message to our text.
                        if(ServerResponse.equalsIgnoreCase("success")) {

                            // If response matched then show the toast.
                            Toast.makeText(STAFFLOGIN.this, "Logged In Successfully", Toast.LENGTH_LONG).show();

                            // Finish the current Login activity.
                            finish();

                            // Opening the user profile activity using intent.
                            Intent intent = new Intent(STAFFLOGIN.this, STAFF.class);

                            // Sending User Email to another activity using intent.
                            intent.putExtra("UserEmailTAG", Email);

                            startActivity(intent);
                        }
                        else {

                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(STAFFLOGIN.this, "Incorrect Login or Password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(STAFFLOGIN.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("STAFF_LOGIN", Email);
                params.put("STAFF_PASSWORD", Password);

                return params;
            }

        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(STAFFLOGIN.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

}