package com.example.minitest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText txtNewTask;
    private Button btnSaveEdit;
    private ImageView ivBack;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        txtNewTask = findViewById(R.id.txtNewTask);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);
        ivBack = findViewById(R.id.ivBack);

        String task = getIntent().getStringExtra("task");
        position = getIntent().getIntExtra("position", -1);

        txtNewTask.setText(task);

        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedTask = txtNewTask.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("editedTask", editedTask);
                resultIntent.putExtra("position", position);
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
