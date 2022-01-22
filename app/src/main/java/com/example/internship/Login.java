package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextInputEditText emailET,passwordET;
    Button login;
    TextView registerNow;
    DatabaseReference databaseReference;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerNow=findViewById(R.id.registerNow);
        login=findViewById(R.id.login);
        emailET=findViewById(R.id.email);
        passwordET=findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = emailET.getText().toString();
                String txt_password = passwordET.getText().toString();
                if(txt_email.isEmpty() || txt_password.isEmpty()){
                    Toast.makeText(Login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginUser(txt_email, txt_password);
                }
            }
        });

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String enteredUsername = usernameET.getText().toString();
//                String enteredPassword = passwordET.getText().toString();
//                if(enteredUsername.isEmpty() || enteredPassword.isEmpty()){
//                    Toast.makeText(Login.this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    databaseReference= FirebaseDatabase.getInstance().getReference("Users");
//                    Query userCheck = databaseReference.orderByChild("Username").equalTo(enteredUsername);
//                    userCheck.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(snapshot.exists()){
//                                String passwordFromDatabase= snapshot.child(enteredUsername).child("Password").getValue(String.class);
//                                if(passwordFromDatabase.equals(enteredPassword)){
//                                    Toast.makeText(Login.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                }
//                                else{
//                                    Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            else{
//                                Toast.makeText(Login.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//        });
    }

    private void loginUser(String email, String password) {
//        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(Login.this, MainActivity.class));
//                finish();
//            }
//        });

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(Login.this, "Credentials don't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}