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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Bulk extends AppCompatActivity {

    TextInputEditText quantity,desc,address1, address2, identity;
    Button donate;
    FirebaseUser user;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk);
        getSupportActionBar().hide();
        quantity=findViewById(R.id.quantity);
        desc=findViewById(R.id.desc);
        address1=findViewById(R.id.address1);
        address2=findViewById(R.id.address2);
        donate=findViewById(R.id.donate);
        identity=findViewById(R.id.identity);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        db = FirebaseFirestore.getInstance();
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity_txt = quantity.getText().toString();
                String desc_txt = desc.getText().toString();
                String address1_txt = address1.getText().toString();
                String address2_txt = address2.getText().toString();
                String identity_txt = identity.getText().toString();
                Map<String,Object> bulk = new HashMap<>();
                bulk.put("quantity",quantity_txt);
                bulk.put("desc",desc_txt);
                bulk.put("address1",address1_txt);
                bulk.put("address2",address2_txt);
                bulk.put("identity",identity_txt);

                db.collection("Orders").document(uid).collection("Bulk Orders").document(identity_txt).set(bulk).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Bulk.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Bulk.this,MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(Bulk.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
