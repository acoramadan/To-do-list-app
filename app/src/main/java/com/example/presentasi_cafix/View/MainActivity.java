package com.example.presentasi_cafix.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.presentasi_cafix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fab).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateTask.class);
            startActivity(intent);
        });

        // Setup reference to Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("tasks");

        // Check if data exists
        checkDataAndLoadFragment();
    }

    private void checkDataAndLoadFragment() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (dataSnapshot.exists()) {
                    TaskFragment taskFragment = new TaskFragment();
                    transaction.replace(R.id.fragment_container, taskFragment);
                } else {
                    EmptyTaskFragment noTaskFragment = new EmptyTaskFragment();
                    transaction.replace(R.id.fragment_container, noTaskFragment);
                }
                transaction.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
