package com.example.appcocherabyron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Dahsboard extends AppCompatActivity {
    TextView tvReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dahsboard);

        tvReturn=findViewById(R.id.tvreturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (Dahsboard.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}