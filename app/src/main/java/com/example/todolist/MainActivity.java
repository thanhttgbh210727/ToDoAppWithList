package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = new ArrayList<>();

        RecyclerView recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onEditClick(Task task) {
                editPosition = taskList.indexOf(task);
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                intent.putExtra("taskName", task.getName());
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }

            @Override
            public void onDeleteClick(Task task) {
                taskList.remove(task);
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewTasks.setAdapter(taskAdapter);

        FloatingActionButton fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String taskName = data.getStringExtra("taskName");

            if (requestCode == REQUEST_CODE_ADD) {
                taskList.add(new Task(taskName));
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
            } else if (requestCode == REQUEST_CODE_EDIT && editPosition != -1) {
                taskList.get(editPosition).setName(taskName);
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
