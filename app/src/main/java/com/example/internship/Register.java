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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private TextInputEditText emailET,numberET,nameET, passwordET1, passwordET2;
    TextView loginNow;
    Button registerBTN;
    DatabaseReference usersReference;

    FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        emailET= findViewById(R.id.email);
        numberET=findViewById(R.id.number);
        nameET=findViewById(R.id.name);
        passwordET1=findViewById(R.id.password1);
        passwordET2=findViewById(R.id.password2);
        registerBTN=findViewById(R.id.register);
        loginNow=findViewById(R.id.loginNow);

        auth = FirebaseAuth.getInstance();

        usersReference = FirebaseDatabase.getInstance().getReference().child("Users");
        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = emailET.getText().toString();
                String txt_name = nameET.getText().toString();
                String txt_number = numberET.getText().toString();
                String txt_password1 = passwordET1.getText().toString();
                String txt_password2 = passwordET2.getText().toString();
                if(txt_email.isEmpty() || txt_password1.isEmpty() || txt_password2.isEmpty()){
                    Toast.makeText(Register.this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                }
                else if(txt_password1.length()<6){
                    Toast.makeText(Register.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (txt_password1.equals(txt_password2)) {
                        registerUser(txt_name, txt_number,txt_email,txt_password1);
                    } else {
                        Toast.makeText(Register.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//        registerBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String usernameString= usernameET.getText().toString();
//                String numberString= numberET.getText().toString();
//                String passwordString= passwordET.getText().toString();
//                if(usernameString.isEmpty() || numberString.isEmpty() || passwordString.isEmpty()){
//                    Toast.makeText(Register.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            databaseReference.child(usernameString).child("Username").setValue(usernameString);
//                            databaseReference.child(usernameString).child("Password").setValue(passwordString);
//                            databaseReference.child(usernameString).child("MobileNumber").setValue(numberString);
//                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                            Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                        }
//                    });
//                }
//            }
//        });
    }

    private void registerUser(String name, String number,String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    createDatabaseValues(name,number,email);
                    startActivity(new Intent(Register.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createDatabaseValues(String name, String number, String email) {
    user = auth.getCurrentUser();

    dataExtract userr = new dataExtract(name,number,email);
    usersReference.child(user.getUid()).setValue(userr);
    }
}