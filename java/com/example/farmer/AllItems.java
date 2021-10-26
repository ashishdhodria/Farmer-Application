package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.farmer.RecyclerView.ItemListAdapter;
import com.example.farmer.data.Item;
import com.example.farmer.listener.ItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllItems extends AppCompatActivity {

    private DatabaseReference mDatabase;
    List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    String getUid;
    private ItemListAdapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);
        recyclerView = findViewById(R.id.recycler);

        getUid = getIntent().getExtras().getString("id");
        getData();
    }
    private void getData() {
        mDatabase = FirebaseDatabase.getInstance().getReference("data").child(getUid);
        itemList.clear();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
                    itemList.add(item);
                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(AllItems.this));
                itemListAdapter = new ItemListAdapter(itemList, AllItems.this, new ItemClickListener() {
                    @Override
                    public void onItemClick(final String id1 , final String id2) {
                        Toast.makeText(AllItems.this, ""+id1, Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView.setAdapter(itemListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AllItems.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}