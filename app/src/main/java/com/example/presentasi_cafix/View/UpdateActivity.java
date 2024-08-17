package com.example.presentasi_cafix.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.example.presentasi_cafix.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    private TextInputEditText taskNameInput, deadlineInput, descriptionInput;
    private AutoCompleteTextView categorySpinner;
    private DatabaseReference databaseReference;
    private String taskId;  // Variable to store task ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize Firebase Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("tasks");

        // Get task ID from intent
        taskId = getIntent().getStringExtra("taskId");

        if (taskId == null) {
            Toast.makeText(this, "Error: Task ID is missing.", Toast.LENGTH_LONG).show();
            finish();  // Exit activity if no ID is found
            return;
        }

        // Set up the back button
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Setup category spinner with an ArrayAdapter
        categorySpinner = findViewById(R.id.category_spinner);
        categorySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new String[]{"Life", "Sport", "Education"}));

        // Initialize input fields
        taskNameInput = findViewById(R.id.task_name);
        deadlineInput = findViewById(R.id.deadline);
        descriptionInput = findViewById(R.id.task_description);

        // Retrieve and set data from intent
        taskNameInput.setText(getIntent().getStringExtra("taskName"));
        categorySpinner.setText(getIntent().getStringExtra("category"), false);
        deadlineInput.setText(getIntent().getStringExtra("deadline"));
        descriptionInput.setText(getIntent().getStringExtra("description"));

        // Set up the update button to save the task
        findViewById(R.id.update_task).setOnClickListener(v -> saveTask());

        // Set up date picker for deadline input
        deadlineInput.setOnClickListener(this::showDatePicker);
    }

    public void showDatePicker(View view) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (datePicker, year, monthOfYear, dayOfMonth) -> deadlineInput.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year)),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void saveTask() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("taskName", taskNameInput.getText().toString());
        updates.put("category", categorySpinner.getText().toString());
        updates.put("deadline", deadlineInput.getText().toString());
        updates.put("description", descriptionInput.getText().toString());

        updateTaskById(updates);
    }

    private void updateTaskById(Map<String, Object> updates) {
        DatabaseReference taskRef = databaseReference.child(taskId);

        taskRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> Toast.makeText(UpdateActivity.this, "Task updated successfully.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(UpdateActivity.this, "Failed to update task.", Toast.LENGTH_SHORT).show());
    }
}
