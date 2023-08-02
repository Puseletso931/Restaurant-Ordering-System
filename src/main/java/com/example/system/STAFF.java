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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class STAFF extends AppCompatActivity {

    TextView textView,showEmail;
    Boolean valid;
    EditText sEmail,cEmail, orderID;
    String status,orderNum,eStaff,eCus,staffMail;
    int staffID,cusID;
    Button changeStatus, btnViewAvg;
    ImageButton addOrder;
    RadioGroup radioStatusGroup;
    RadioButton pend,ready,col,radioStatus;
    int[] IDs=new int[2];
    final int[] GBT = new int[3];

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    private String statusURL = "https://lamp.ms.wits.ac.za/~s2341162/staffChangeOrderStatus.php";
    private String addURL="https://lamp.ms.wits.ac.za/~s2341162/addOrder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        textView = findViewById(R.id.showEmail);
        staffMail = getIntent().getStringExtra("UserEmailTAG");
        textView.setText(staffMail);

        //ADD ORDER STUFF
        sEmail=findViewById(R.id.AddstaffEmail);
        cEmail=findViewById(R.id.AddCEmail);
        addOrder=findViewById(R.id.addOrder);

        //CHANGE ORDER STATUS STUFF
        orderID=findViewById(R.id.orderID);
        changeStatus=findViewById(R.id.changeOStatus);
        radioStatusGroup=findViewById(R.id.radioGroup);
        pend=findViewById(R.id.statusPending);
        ready=findViewById(R.id.statusReady);
        col=findViewById(R.id.statusCollected);

        //VIEW AVERAGE RATING
        showEmail=findViewById(R.id.showEmail);
        btnViewAvg=findViewById(R.id.btnViewAvg);

        status=orderNum=eCus=eStaff="";
        requestQueue = Volley.newRequestQueue(STAFF.this);

        progressDialog = new ProgressDialog(STAFF.this);

        btnViewAvg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(STAFF.this, StaffAveRating.class);
                intent.putExtra("StaffTAG", staffMail);
                startActivity(intent);
            }
        });

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAddInput();
                if(valid){
                    getIDs();
                    AddOrder();
                }
                else{
                    Toast.makeText(STAFF.this, "Please fill in all the required fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStatusInput();
                if(valid){
                    changeStatus();
                }
                else {

                    Toast.makeText(STAFF.this, "Please fill in all the required fields.", Toast.LENGTH_LONG).show();

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

    public void checkStatusInput() {
        orderNum=orderID.getText().toString().trim();

        int selected=radioStatusGroup.getCheckedRadioButtonId();
        radioStatus=(RadioButton)findViewById(selected);
        status=(radioStatus.getText().toString().trim()).toUpperCase();

        if(!pend.isChecked() && !ready.isChecked() && !col.isChecked()){ //if none are checked
            //Toast.makeText(STAFF.this,"Please select status. (Default: PENDING)",Toast.LENGTH_LONG).show();
            status="PENDING"; //default
        }

        if (isEmpty(orderID)) {
            orderID.setError("Order ID/Order Number is required!");
            valid = false;
        } else {
            String str = orderID.getText().toString();
            int id = Integer.parseInt(str);
            if (id < 0 || id < 37) //37 is where the orderIDs auto-increment start
            {
                valid = false;
            } else {
                valid = true;
            }
        }
        if(!pend.isChecked() && !ready.isChecked() && !col.isChecked()){ //if none are checked
            //Toast.makeText(STAFF.this,"Please select status. (Default: PENDING)",Toast.LENGTH_LONG).show();
            status="PENDING";
        }
    }

    public void checkAddInput(){
        eCus=cEmail.getText().toString().trim();
        eStaff=sEmail.getText().toString().trim();

        if(isEmpty(sEmail)){
            sEmail.setError("Staff Email is required!");
            valid=false;
        }
        else if(!isEmpty(sEmail) && !isEmail(sEmail)){
            sEmail.setError("Enter valid email!");
            valid=false;
        }
        else{
            valid=true;
        }

        if(isEmpty(cEmail)){
            cEmail.setError("Customer Email is required!");
            valid=false;
        }
        else if(!isEmpty(cEmail) && !isEmail(cEmail)){
            cEmail.setError("Enter valid email!");
            valid=false;
        }
        else{
            valid=true;
        }
    }

    public void changeStatus(){
        progressDialog.setMessage("Please Wait, We are Updating Your Data on Server");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, statusURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.equals("success")) {
                    Toast.makeText(STAFF.this, "Status changed successfully:)", Toast.LENGTH_LONG).show();
                } else if (response.equals("failure")) {
                    Toast.makeText(STAFF.this, "Try Again", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(STAFF.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("ORDER_ID", orderNum);
                data.put("ORDER_STATUS", status);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getIDs(){
        OkHttpClient client=new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://lamp.ms.wits.ac.za/~s2341162/getIDs.php").newBuilder();
        urlBuilder.addQueryParameter("CUSTOMER_EMAIL",eCus);
        urlBuilder.addQueryParameter("STAFF_LOGIN",eStaff);

        String url = urlBuilder.build().toString();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                // ... check for failure using `isSuccessful` before proceeding
                try{
                    // Read data on the worker thread
                    String responseData = response.body().string();

                    /*STAFF.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView testing= findViewById(R.id.test);
                            testing.setText(responseData);
                        }
                    });*/
                    JSONArray json=new JSONArray(responseData);
                    JSONObject c=json.getJSONObject(0);
                    cusID=c.getInt("CUSTOMER_ID");
                    staffID=c.getInt("STAFF_ID");

                    IDs[0]=cusID;
                    IDs[1]=staffID;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void AddOrder(){
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, addURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.equals("success")) {
                    Toast.makeText(STAFF.this, "Order Added Successfully :)", Toast.LENGTH_LONG).show();
                } else if (response.equals("failure")) {
                    Toast.makeText(STAFF.this, "Try Again", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(STAFF.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                int c=IDs[0];
                int s=IDs[1];
                String customerID=String.valueOf(c);
                String staffID=String.valueOf(s);
                data.put("CUSTOMER_ID", customerID);
                data.put("STAFF_ID", staffID);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}