package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(Settings.this,MainActivity.class));
                    finish();
                    break;
                case R.id.profile:
                    startActivity(new Intent(Settings.this,Profile.class));
                    finish();
                    break;
                case R.id.settings:
                    startActivity(new Intent(Settings.this,Settings.class));
                    finish();
                    break;
            }

            return true;
        });
    }
}