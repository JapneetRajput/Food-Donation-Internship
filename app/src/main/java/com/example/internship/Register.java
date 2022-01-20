package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    TextInputEditText usernameET,numberET, passwordET;
    TextView loginNow;
    Button registerBTN;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        usernameET= findViewById(R.id.username);
        numberET=findViewById(R.id.number);
        passwordET=findViewById(R.id.password);
        registerBTN=findViewById(R.id.register);
        loginNow=findViewById(R.id.loginNow);

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io = new Intent(Register.this, Login.class);
                startActivity(io);
            }
        });

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString= usernameET.getText().toString();
                String numberString= numberET.getText().toString();
                String passwordString= passwordET.getText().toString();
                if(usernameString.isEmpty() || numberString.isEmpty() || passwordString.isEmpty()){
                    Toast.makeText(Register.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(usernameString).child("Username").setValue(usernameString);
                            databaseReference.child(usernameString).child("Password").setValue(passwordString);
                            databaseReference.child(usernameString).child("MobileNumber").setValue(numberString);
                            startActivity(new Intent(getApplicationContext(),landing.class));
                            Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }
}