package com.example.minitest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnAdd, btnEdit;
    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;
    private int selectedTaskPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTaskPosition != -1) {
                    Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                    intent.putExtra("task", taskList.get(selectedTaskPosition));
                    intent.putExtra("position", selectedTaskPosition);
                    startActivityForResult(intent, 2);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTaskPosition = position;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String newTask = data.getStringExtra("newTask");
            taskList.add(newTask);
            adapter.notifyDataSetChanged();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            String editedTask = data.getStringExtra("editedTask");
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                taskList.set(position, editedTask);
                adapter.notifyDataSetChanged();
            }
        }
    }
}