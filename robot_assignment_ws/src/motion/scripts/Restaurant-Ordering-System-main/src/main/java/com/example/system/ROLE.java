package com.example.system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ROLE extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        Button rbutton=(findViewById(R.id.btnHomeRegister));
        Button lbutton=(findViewById(R.id.btnHomeLogin));
        rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ROLE.this,REGISTER.class);
                startActivity(intent);
            }
        });
        lbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ROLE.this,Login.class);
                startActivity(intent);
            }
        });
    }
}