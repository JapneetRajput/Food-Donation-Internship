package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {

    Button login, register, guest;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference usersReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        guest=findViewById(R.id.guest);
        auth=FirebaseAuth.getInstance();
        usersReference=FirebaseDatabase.getInstance().getReference().child("Users");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {

        auth.signInAnonymously()
                .addOnCompleteListener(StartActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(StartActivity.this, "Logged in as a guest",Toast.LENGTH_SHORT).show();
//                            createDatabaseValues();
                            startActivity(new Intent(StartActivity.this,MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(StartActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createDatabaseValues() {
        user = auth.getCurrentUser();

        dataExtract userr = new dataExtract("Guest name","Guest number","Guest email");
        usersReference.child(user.getUid()).setValue(userr);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(StartActivity.this,MainActivity.class));
            finish();
        }
        else{
            Toast.makeText(StartActivity.this, "Login/Register", Toast.LENGTH_SHORT).show();
        }
    }
}