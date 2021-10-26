package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.farmer.RecyclerView.MarketListAdapter;
import com.example.farmer.data.Item;
import com.example.farmer.data.User;
import com.example.farmer.listener.ItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Market_Price extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseFirestore db;
    List<User> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MarketListAdapter marketListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market__price);

        recyclerView = findViewById(R.id.recycler);
        db = FirebaseFirestore.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference("data");

        getData();
    }

    private void getData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    db.collection("users").document(postSnapshot.getRef().getKey()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    User user1 = task.getResult().toObject(User.class);
                                    userList.add(user1);
                                }

                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Market_Price.this));
                                marketListAdapter = new MarketListAdapter(userList, Market_Price.this, new ItemClickListener() {
                                    @Override
                                    public void onItemClick(String id1, String id2) {
                                        if (id2=="1") {
                                            Intent intent = new Intent(Market_Price.this, AllItems.class);
                                            intent.putExtra("id", id1);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(Market_Price.this, "Calling", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                recyclerView.setAdapter(marketListAdapter);
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}