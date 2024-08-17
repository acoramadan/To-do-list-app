package com.example.presentasi_cafix.Model;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.presentasi_cafix.Model.Taskhehe;
import com.example.presentasi_cafix.R;
import com.example.presentasi_cafix.View.TaskDetails; // Pastikan import ini benar
import com.example.presentasi_cafix.View.TaskDetails;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Taskhehe> taskList;

    public TaskAdapter(List<Taskhehe> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Taskhehe task = taskList.get(position);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TaskDetails.class);
            intent.putExtra("taskTitle", task.getTaskName());
            intent.putExtra("taskDate", task.getDeadline());
            intent.putExtra("taskDescription", task.getDescription());
            v.getContext().startActivity(intent);
        });
        holder.taskTitle.setText(task.getTaskName());
        holder.taskDate.setText(task.getDeadline());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskDate;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDate = itemView.findViewById(R.id.task_date);
        }
    }
}
