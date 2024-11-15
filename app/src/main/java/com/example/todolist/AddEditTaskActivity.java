package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditTaskActivity extends AppCompatActivity {

    private EditText editTextTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        editTextTaskName = findViewById(R.id.editTextTaskName);
        Button buttonSave = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("taskName")) {
            editTextTaskName.setText(intent.getStringExtra("taskName"));
        }

        buttonSave.setOnClickListener(v -> {
            String taskName = editTextTaskName.getText().toString().trim();
            if (!taskName.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("taskName", taskName);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Please enter a task name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
