package com.example.system;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.util.TypedValue;
import android.view.View;

import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.*;

public class MCD extends AppCompatActivity {
    TextView textView;
    private final String URL = "https://lamp.ms.wits.ac.za/~s2341162/rate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcd);
        textView = findViewById(R.id.emailholder);
        String email = getIntent().getStringExtra("UserEmailTAG");
        String m=textView.getText().toString()+ email;
       textView.setText(m);



        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://lamp.ms.wits.ac.za/~s2341162/customerOrders.php").newBuilder();
        urlBuilder.addQueryParameter("CUSTOMER_EMAIL", email);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //try{
                // ... check for failure using `isSuccessful` before proceeding
                // Read data on the worker thread
                String responseData = response.body().string();
                //JSONArray orders=new JSONArray(responseData);
                String[] replace = responseData.toString().split("\n");
                // Run view-related code back on the main thread
                MCD.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setter(replace);

                      //  TextView mTextView = (TextView) findViewById(R.id.T2);
                        //mTextView.setText(replace[1]);
                    }
                });
                // }
                /*catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });


    }
    public void setter(String [] arr) {
        EditText ORDER_ID=(findViewById(R.id.editTextTextPersonName));
        ImageView TU=(findViewById(R.id.thumbsup));
        ImageView TD=(findViewById(R.id.thumbsdown));

        int number = arr.length; // number of buttons
        LinearLayout layout = (LinearLayout) findViewById(R.id.content);
        for (int i = 0; i < number; i++) {
            TextView m = new TextView(this);
            m.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            m.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
            m.setText(arr[i]);
            layout.addView(m);
        }

       TU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {


                                if (ServerResponse.equalsIgnoreCase("success")) {

                                    Toast.makeText(MCD.this, "Rated Successfully", Toast.LENGTH_LONG).show();

                                }
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {


                                // Showing error message if something goes wrong.
                                Toast.makeText(MCD.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {


                        // Creating Map String Params.
                        Map<String, String> params = new HashMap<String, String>();
                        String  order_id=ORDER_ID.getText().toString().trim();
                        params.put("ORDER_ID", order_id);
                        params.put("ORDER_RATING", "1");

                        return params;
                    }

                };

                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(MCD.this);

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);

            }
        });
        TD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {


                                // Matching server responce message to our text.
                                if (ServerResponse.equalsIgnoreCase("success")) {

                                    // If response matched then show the toast.
                                    Toast.makeText(MCD.this, "Rated Successfully", Toast.LENGTH_LONG).show();

                                }
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {


                                // Showing error message if something goes wrong.
                                Toast.makeText(MCD.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {


                        // Creating Map String Params.
                        Map<String, String> params = new HashMap<String, String>();
                        String  order_id=ORDER_ID.getText().toString().trim();
                        params.put("ORDER_ID", order_id);
                        params.put("ORDER_RATING", "-1");

                        return params;
                    }

                };

                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(MCD.this);

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);

            }
        });



    }
}