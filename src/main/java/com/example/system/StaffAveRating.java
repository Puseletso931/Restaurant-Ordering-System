package com.example.system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class StaffAveRating extends AppCompatActivity {
TextView staffEmail,aveRate,nonrated,total;
EditText EnterStaffID;
String staffmail, email;
Button OkBtn;
int staff_id;
String staffID;
final int[] id=new int [1];
final int[] GBT = new int[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_ave_rating);
        staffEmail = findViewById(R.id.textView14);
        aveRate=findViewById(R.id.avRate);
        nonrated=findViewById(R.id.nonrated);
        total=findViewById(R.id.total);
        staffmail = getIntent().getStringExtra("StaffTAG");
        staffEmail.setText("Staff: "+staffmail);
        OkBtn=findViewById(R.id.okBtn);
        EnterStaffID=findViewById(R.id.EnterStaffID);


        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                average();
            }
        });
        //staffID=String.valueOf(staff_id);

    }
    public void average(){
        OkHttpClient client2=new OkHttpClient();

        HttpUrl.Builder urlBuilder2 = HttpUrl.parse("https://lamp.ms.wits.ac.za/~s2341162/avgRate.php").newBuilder();
        urlBuilder2.addQueryParameter("STAFF_ID", staffID=EnterStaffID.getText().toString().trim());

        String url2 = urlBuilder2.build().toString();

        okhttp3.Request request2 = new okhttp3.Request.Builder()
                .url(url2)
                .build();

        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call2, @NotNull IOException e) {
            }
            @Override
            public void onResponse(Call call2, final okhttp3.Response response2) throws IOException {
                // ... check for failure using `isSuccessful` before proceeding
                try{
                    // Read data on the worker thread
                    String responseData = response2.body().string();
                    JSONArray arr = new JSONArray(responseData);
                    for(int i=0;i<arr.length();i++){
                        JSONObject o= arr.getJSONObject(i);
                        GBT[i]=o.getInt("GoodBadTtotal");
                    }

                    final double avGood=((double)GBT[0]/(double)GBT[2])*100.00; //good percentage=(good/total)*100
                    final double noRate=GBT[2]-GBT[0]-GBT[1]; //nonrated=total-good-bad
                    //final double totalOrders=GBT[2];
                    StaffAveRating.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView ave=findViewById(R.id.avRate);
                            TextView norate=findViewById(R.id.nonrated);
                            TextView totalOrders=findViewById(R.id.total);
                            TextView testing=findViewById(R.id.testing);

                            testing.setText(String.valueOf(staffID));

                            ave.setText(ave.getText()+String.valueOf((int)avGood)+"% Good");
                            norate.setText(norate.getText()+String.valueOf((int)noRate));
                            totalOrders.setText(totalOrders.getText()+String.valueOf(GBT[2]));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
