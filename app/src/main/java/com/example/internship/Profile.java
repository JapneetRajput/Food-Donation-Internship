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

public class Profile extends AppCompatActivity {

    Button logout;
    TextView nameTV, numberTV, emailTV;
    BottomNavigationView bottomNavigationView;
    DatabaseReference usersReference;

    FirebaseUser user;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        nameTV= findViewById(R.id.name);
        numberTV= findViewById(R.id.number);
        emailTV= findViewById(R.id.email);
        logout = findViewById(R.id.button);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        usersReference = FirebaseDatabase.getInstance().getReference().child("Users");
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = "Name : " + snapshot.child(uid).child("name").getValue(String.class);
                String number = "Number : " +snapshot.child(uid).child("number").getValue(String.class);
                String email = "Email : " +snapshot.child(uid).child("email").getValue(String.class);

                nameTV.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bottomNavigationView.setSelectedItemId(R.id.profile);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Profile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profile.this,StartActivity.class));
                finish();
            }
        });
    }
}