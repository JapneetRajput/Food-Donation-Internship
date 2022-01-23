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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {

    TextInputEditText nameET,numberET;
    Button update;
    DatabaseReference usersReference;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        nameET=findViewById(R.id.name);
        numberET=findViewById(R.id.number);
        update=findViewById(R.id.update);
        user= FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        usersReference = FirebaseDatabase.getInstance().getReference().child("Users");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String number = numberET.getText().toString();
                HashMap User = new HashMap();
                User.put("name",name);
                User.put("number",number);
                usersReference.child(uid).updateChildren(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            nameET.setText("");
                            numberET.setText("");
                            startActivity(new Intent(UpdateProfile.this,Settings.class));
                            finish();
                            Toast.makeText(UpdateProfile.this, "PastOrders updated", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(UpdateProfile.this, "Failed to update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}