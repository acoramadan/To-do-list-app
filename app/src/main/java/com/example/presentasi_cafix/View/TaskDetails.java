package com.example.presentasi_cafix.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.presentasi_cafix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaskDetails extends AppCompatActivity {

    private DatabaseReference databaseReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        databaseReference = FirebaseDatabase.getInstance().getReference("tasks");
        TextView title = findViewById(R.id.task_title);
        TextView date = findViewById(R.id.task_date);
        TextView description = findViewById(R.id.task_description);
        ImageView backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> {
            finish();
        });

        String taskTitle = getIntent().getStringExtra("taskTitle");
        String taskDate = getIntent().getStringExtra("taskDate");
        String taskDescription = getIntent().getStringExtra("taskDescription");

        title.setText(taskTitle);
        date.setText(taskDate);
        description.setText(taskDescription);

        findViewById(R.id.edit_button).setOnClickListener(v -> {
            Intent editIntent = new Intent(TaskDetails.this, UpdateActivity.class);
            editIntent.putExtra("taskTitle", taskTitle);
            editIntent.putExtra("taskDate", taskDate);
            editIntent.putExtra("taskDescription", taskDescription);
            startActivity(editIntent);
        });

        findViewById(R.id.delete_button).setOnClickListener(v -> {
            deleteTaskFromDatabase(taskTitle);
        });
    }

    private void deleteTaskFromDatabase(String taskTitle) {
        databaseReference.orderByChild("taskName").equalTo(taskTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TaskDetailActivity", "onCancelled", databaseError.toException());
            }
        });
    }
}