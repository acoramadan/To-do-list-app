package com.example.presentasi_cafix.View;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.presentasi_cafix.Model.Taskhehe;
import com.example.presentasi_cafix.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Random;

public class CreateTask extends AppCompatActivity {

    private TextInputEditText taskNameEditText, deadlineEditText, descriptionEditText;
    private DatabaseReference databaseReference;
    private AutoCompleteTextView categoryEditText;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        taskNameEditText = findViewById(R.id.task_name);
        categoryEditText = findViewById(R.id.category);
        deadlineEditText = findViewById(R.id.deadline);
        descriptionEditText = findViewById(R.id.task_description);

        // Inisialisasi Database Realtime
        databaseReference = FirebaseDatabase.getInstance().getReference("tasks");

        String[] categories = new String[]{"Life", "Sport", "Education"};

        // Buat ArrayAdapter dan setel ke AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        categoryEditText.setAdapter(adapter);

        findViewById(R.id.create_task_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });
    }

    private void createTask() {
        String taskName = taskNameEditText.getText().toString().trim();
        String deadline = deadlineEditText.getText().toString().trim();
        String category = categoryEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (TextUtils.isEmpty(taskName) || TextUtils.isEmpty(category) || TextUtils.isEmpty(deadline) || TextUtils.isEmpty(description)) {
            Toast.makeText(CreateTask.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat ID unik untuk setiap task
        String taskId = databaseReference.push().getKey();
        Random random = new Random();
        int rand = random.nextInt(1000);
        String id = "id_"+rand;
        Taskhehe task = new Taskhehe(taskName, category, deadline, description,id);

        if (taskId != null) {
            databaseReference.child(taskId).setValue(task)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(CreateTask.this, "Task Created", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(CreateTask.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    public void showDatePicker(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view1, year1, monthOfYear, dayOfMonth) -> {

                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    deadlineEditText.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }
}
