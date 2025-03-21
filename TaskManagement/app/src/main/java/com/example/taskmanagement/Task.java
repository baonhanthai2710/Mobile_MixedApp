package com.example.taskmanagement;

public class Task {
    private int id;
    private String taskText;
    private String time;

    public Task(int id, String taskText, String time) {
        this.id = id;
        this.taskText = taskText;
        this.time = time;
    }

    public int getId() { return id; }
    public String getTaskText() { return taskText; }
    public String getTime() { return time; }

    public void setTaskText(String taskText) { this.taskText = taskText; }
    public void setTime(String time) { this.time = time; }
}
