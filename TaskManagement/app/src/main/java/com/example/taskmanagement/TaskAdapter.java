package com.example.taskmanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> taskList;
    private Context context;
    private OnTaskChangeListener listener;

    public interface OnTaskChangeListener {
        void onTaskUpdated();
    }

    public TaskAdapter(Context context, ArrayList<Task> taskList, OnTaskChangeListener listener) {
        this.context = context;
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.textViewTask.setText(task.getTaskText());
        holder.textViewTime.setText(task.getTime());

        // Xử lý nút Edit
        holder.buttonEdit.setOnClickListener(v -> showEditDialog(task, position));

        // Xử lý nút Delete
        holder.buttonDelete.setOnClickListener(v -> {
            taskList.remove(position);
            notifyItemRemoved(position);
            listener.onTaskUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTask, textViewTime;
        Button buttonEdit, buttonDelete;

        public TaskViewHolder(View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    // Hiển thị hộp thoại chỉnh sửa
    private void showEditDialog(Task task, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_task, null);
        builder.setView(dialogView);

        EditText editTask = dialogView.findViewById(R.id.editTextTask);
        EditText editTime = dialogView.findViewById(R.id.editTextTime);
        Button buttonSave = dialogView.findViewById(R.id.buttonSave);

        editTask.setText(task.getTaskText());
        editTime.setText(task.getTime());

        AlertDialog dialog = builder.create();
        dialog.show();

        buttonSave.setOnClickListener(v -> {
            task.setTaskText(editTask.getText().toString());
            task.setTime(editTime.getText().toString());
            notifyItemChanged(position);
            listener.onTaskUpdated();
            dialog.dismiss();
        });
    }
}
