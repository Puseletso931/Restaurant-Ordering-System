package com.example.system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button  rbutton=(findViewById(R.id.STAFF));
        Button lbutton=(findViewById(R.id.CUSTOMER));
       rbutton.setOnClickListener(new View.OnClickListener() {
         @Override
          public void onClick(View v) {
              Intent intent = new Intent(MainActivity.this,STAFFREGISTER.class);
               startActivity(intent);
            }
        });
        lbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ROLE.class);
                startActivity(intent);
            }
        });

    }


}