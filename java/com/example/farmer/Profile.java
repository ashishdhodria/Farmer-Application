package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farmer.data.Item;
import com.example.farmer.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class Profile extends AppCompatActivity {

    private EditText name, state, district, market;
    private String uname=" ", ustate=" ", udistrict=" ", umarket=" ";
    FirebaseFirestore db;
    FirebaseUser user;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.name);
        state = findViewById(R.id.state);
        district = findViewById(R.id.district);
        market = findViewById(R.id.market);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user1 = task.getResult().toObject(User.class);
                        name.setText(user1.getName());
                        state.setText(user1.getState());
                        district.setText(user1.getDistrict());
                        market.setText(user1.getMarket());
                    }
                }
            }
        });
    }

    public void saveData(View view) {
        uname = name.getText().toString();
        ustate = state.getText().toString();
        udistrict = district.getText().toString();
        umarket = market.getText().toString();

        User user1 = new User(user.getUid(), uname, user.getPhoneNumber(), ustate, udistrict, umarket);
        db.collection("users").document(user.getUid()).set(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Profile.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

}