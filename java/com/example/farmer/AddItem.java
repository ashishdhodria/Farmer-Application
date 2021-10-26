package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farmer.RecyclerView.ItemListAdapter;
import com.example.farmer.data.Item;
import com.example.farmer.listener.ItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddItem extends AppCompatActivity {

    FirebaseUser user;
    private EditText commodity, price;
    private DatabaseReference mDatabase, getmDatabase;
    List<Item> itemList = new ArrayList<>();

    private RecyclerView recyclerView;
    private ItemListAdapter itemListAdapter;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        user = FirebaseAuth.getInstance().getCurrentUser();
        commodity = findViewById(R.id.commodity);
        price = findViewById(R.id.price);
        recyclerView = findViewById(R.id.recycler);

        getData();
    }

    private void getData() {
        getmDatabase = FirebaseDatabase.getInstance().getReference("data").child(user.getUid());
        itemList.clear();
        getmDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
                    itemList.add(item);
                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(AddItem.this));
                itemListAdapter = new ItemListAdapter(itemList, AddItem.this, new ItemClickListener() {
                    @Override
                    public void onItemClick(final String id1, final String id2) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddItem.this);
                        alertDialog.setTitle("Remove");
                        alertDialog.setMessage("Do you want to remove Item?");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getmDatabase.child(id1).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(AddItem.this, "Removed", Toast.LENGTH_SHORT).show();
                                        getData();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddItem.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Toast.makeText(AddItem.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertDialog.show();
                    }
                });
                recyclerView.setAdapter(itemListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddItem.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void save(View view) {
        String ucommodity = commodity.getText().toString();
        String uprice = price.getText().toString();
        String uniqueID = UUID.randomUUID().toString();

        Item item = new Item(ucommodity, uprice, uniqueID);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("data").child(user.getUid()).child(uniqueID).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddItem.this, "Saved", Toast.LENGTH_SHORT).show();
                commodity.setText(" ");
                price.setText(" ");
                getData();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddItem.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}