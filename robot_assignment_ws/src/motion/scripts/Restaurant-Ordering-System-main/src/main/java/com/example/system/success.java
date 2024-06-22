package com.example.system;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class success extends AppCompatActivity {

    View b,a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        b = (findViewById(R.id.back_pressed));
        String email = getIntent().getStringExtra("UserEmailTAG");
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(success.this, MainActivity.class);
                startActivity(intent);
            }
        });

        a = (findViewById(R.id.shopping));
        a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(success.this,MCD.class);
                intent.putExtra("UserEmailTAG", email);
                startActivity(intent);
            }
        });




    }

}
