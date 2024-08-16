package com.example.presentasi_cafix.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.presentasi_cafix.Model.Taskhehe;
import com.example.presentasi_cafix.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateTask extends AppCompatActivity {

    private TextInputEditText taskNameEditText, categoryEditText,deadlineEditText, descriptionEditText;
    private FirebaseFirestore db;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);


        db = FirebaseFirestore.getInstance();


        taskNameEditText = findViewById(R.id.task_name);

        categoryEditText = findViewById(R.id.category);
        deadlineEditText = findViewById(R.id.deadline);
        descriptionEditText = findViewById(R.id.task_description);


        findViewById(R.id.create_task_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });
    }

    private void createTask() {
        String taskName = taskNameEditText.getText().toString().trim();
        String category = categoryEditText.getText().toString().trim();
        String deadline = deadlineEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (TextUtils.isEmpty(taskName) || TextUtils.isEmpty(category) || TextUtils.isEmpty(deadline) || TextUtils.isEmpty(description)) {
            Toast.makeText(CreateTask.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Taskhehe task = new Taskhehe(taskName, category, deadline, description);

        db.collection("tasks")
                .add(task)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(CreateTask.this, "Task Created", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(CreateTask.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
