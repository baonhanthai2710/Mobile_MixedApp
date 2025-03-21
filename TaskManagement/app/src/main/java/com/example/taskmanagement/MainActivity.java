package com.example.taskmanagement;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskChangeListener {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<Task> taskList;
    private EditText editTextTask, editTextTime;
    private Button buttonAdd;
    private int taskIdCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        editTextTask = findViewById(R.id.editTextTask);
        editTextTime = findViewById(R.id.editTextTime);
        buttonAdd = findViewById(R.id.buttonAdd);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(this, taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        buttonAdd.setOnClickListener(view -> addTask());

        // Yêu cầu quyền thông báo trên Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
        }
    }

    private void addTask() {
        String taskText = editTextTask.getText().toString();
        String timeText = editTextTime.getText().toString();

        if (!taskText.isEmpty() && !timeText.isEmpty()) {
            Task task = new Task(taskIdCounter++, taskText, timeText);
            taskList.add(task);
            adapter.notifyDataSetChanged();
            scheduleNotification(task);
            editTextTask.setText("");
            editTextTime.setText("");
            Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter both task and time!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification(Task task) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("task", task.getTaskText());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, task.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            Calendar setTime = Calendar.getInstance();
            setTime.setTime(sdf.parse(task.getTime()));
            calendar.set(Calendar.HOUR_OF_DAY, setTime.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, setTime.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, 0);

            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DAY_OF_MONTH, 1); // Nếu thời gian đã qua, đặt vào ngày hôm sau
            }

            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(this, "Notification set for " + task.getTime(), Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid time format! Use HH:mm", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTaskUpdated() {
        adapter.notifyDataSetChanged();
    }
}