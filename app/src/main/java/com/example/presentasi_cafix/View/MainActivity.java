package com.example.presentasi_cafix.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.presentasi_cafix.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            EmptyTaskFragment noTaskFragment = new EmptyTaskFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, noTaskFragment);
            transaction.commit();
        }

        findViewById(R.id.fab).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateTask.class);
            startActivity(intent);
        });
    }
}
