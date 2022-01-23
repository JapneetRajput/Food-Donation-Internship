package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {

    Button logout,editProfile;
    BottomNavigationView bottomNavigationView;
    DatabaseReference usersReference;

    TextView nameTV, numberTV, emailTV;
    FirebaseUser user;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        // buttons
        logout = findViewById(R.id.logout);
        editProfile=findViewById(R.id.editProfile);

        nameTV= findViewById(R.id.name);
        numberTV= findViewById(R.id.number);
        emailTV= findViewById(R.id.email);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(Settings.this,MainActivity.class));
                    finish();
                    break;
                case R.id.pastOrder:
                    startActivity(new Intent(Settings.this, PastOrders.class));
                    finish();
                    break;
                case R.id.settings:
                    startActivity(new Intent(Settings.this,Settings.class));
                    finish();
                    break;
            }

            return true;
        });

        auth = FirebaseAuth.getInstance();
        usersReference = FirebaseDatabase.getInstance().getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = "Name : " + snapshot.child(uid).child("name").getValue(String.class);
                String number = "Number : " +snapshot.child(uid).child("number").getValue(String.class);
                String email = "Email : " +snapshot.child(uid).child("email").getValue(String.class);

                nameTV.setText(name);
                numberTV.setText(number);
                emailTV.setText(email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Settings.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.this,StartActivity.class));
                finish();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,UpdateProfile.class));
                finish();
            }
        });
    }
}