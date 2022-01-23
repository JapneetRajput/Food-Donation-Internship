package com.example.internship;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PastOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterPastOrders adapterPastOrders;
    ArrayList<PastOrderList> list;
    BottomNavigationView bottomNavigationView;
    FirebaseFirestore db;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.pastOrder);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();

        recyclerView=findViewById(R.id.pastOrderRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<PastOrderList>();
        adapterPastOrders = new AdapterPastOrders(this,list);
        db=FirebaseFirestore.getInstance();

        recyclerView.setAdapter(adapterPastOrders);


        EventChangeListener();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(PastOrders.this,MainActivity.class));
                    finish();
                    break;
                case R.id.pastOrder:
                    startActivity(new Intent(PastOrders.this, PastOrders.class));
                    finish();
                    break;
                case R.id.settings:
                    startActivity(new Intent(PastOrders.this,Settings.class));
                    finish();
                    break;
            }

            return true;
        });

    }

    private void EventChangeListener() {

        db.collection("Orders").document(uid).collection("Bulk Orders")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(PastOrders.this, "Snapshot error", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            for(DocumentChange dc : value.getDocumentChanges()){

                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();

                                if(dc.getType() == DocumentChange.Type.ADDED){
                                    list.add(dc.getDocument().toObject(PastOrderList.class));
                                }

                                adapterPastOrders.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }
}