package com.example.minitest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText txtNewTask;
    private Button btnSaveNew;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        txtNewTask = findViewById(R.id.txtNewTask);
        btnSaveNew = findViewById(R.id.btnSaveNew);
        ivBack = findViewById(R.id.ivBack);

        btnSaveNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTask = txtNewTask.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newTask", newTask);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
