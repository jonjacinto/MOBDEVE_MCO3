package com.mobdeve.s19.jacinto.jonpiolo.mco3;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s19.jacinto.jonpiolo.mco3.databinding.DialogueAddTaskBinding;
import com.mobdeve.s19.jacinto.jonpiolo.mco3.databinding.TaskItemBinding;

import java.time.LocalDate;
import java.util.ArrayList;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    private ArrayList<ToDoTaskItem> toDoTaskItems;
    private Activity activity;

    public TaskAdapter(ArrayList<ToDoTaskItem> toDoTaskItems, Activity activity) {
        this.toDoTaskItems = toDoTaskItems;
        this.activity = activity;
    }
    @Override
    public int getItemCount() {
        return this.toDoTaskItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindToDoTaskItem(this.toDoTaskItems.get(position), position);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskItemBinding itemBinding = TaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(itemBinding);
    }

    public void updateToDoTaskItem(int position, ToDoTaskItem toDoTaskItem) {
        this.toDoTaskItems.set(position, toDoTaskItem);
        notifyItemChanged(position);
    }

    public void addToDoTaskItem(ToDoTaskItem toDoTaskItem) {
        this.toDoTaskItems.add(toDoTaskItem);
        notifyItemInserted(this.toDoTaskItems.size() - 1);
    }

    public void removeToDoTaskItem(int position) {
        ToDoDatabase toDoDatabase= new ToDoDatabase(activity.getApplicationContext());
        ToDoTaskItem taskItem =this.toDoTaskItems.get(position);
        toDoDatabase.deleteTask(taskItem);

        this.toDoTaskItems.remove(position);
        notifyItemRemoved(position);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TaskItemBinding itemBinding;

        private int myPos =-1;
        private ToDoTaskItem item;

        public TaskViewHolder(TaskItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            // Allows for the itemView to trigger the logic in OnClick()
            this.itemView.setOnClickListener(this);
        }

        public void bindToDoTaskItem(ToDoTaskItem toDoTaskItem, int position){
            this.myPos = position;
            this.item =toDoTaskItem;

            this.itemBinding.taskTitle.setText(this.item.getTitle());
            this.itemBinding.taskTag.setText(this.item.getTaskTag());
            this.itemBinding.taskDeadline.setText(this.item.getTaskDeadline().toString());

            this.itemBinding.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeToDoTaskItem(position);
                }
            });
        }


        @Override
        public void onClick(View view) {
            showCustomDialogue().show();
        }

        private Dialog showCustomDialogue() {
            DialogueAddTaskBinding dialogueAddTaskBinding = DialogueAddTaskBinding.inflate(activity.getLayoutInflater());

            dialogueAddTaskBinding.taskAddTitle.setText(item.getTitle());
            dialogueAddTaskBinding.taskAddTag.setText(item.getTaskTag());

            dialogueAddTaskBinding.taskDeadline.updateDate(item.getTaskDeadline().getYear(), item.getTaskDeadline().getMonthValue() - 1, item.getTaskDeadline().getDayOfMonth());

            return new AlertDialog.Builder(activity)
                    .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {
                            ToDoDatabase toDoDatabase = new ToDoDatabase(activity.getApplicationContext());
                            ToDoTaskItem taskItem = null;
                            taskItem = new ToDoTaskItem(
                                    item.getTaskId(),
                                    dialogueAddTaskBinding.taskAddTitle.getText().toString(),
                                    dialogueAddTaskBinding.taskAddTag.getText().toString(),
                                    LocalDate.of(dialogueAddTaskBinding.taskDeadline.getYear(), dialogueAddTaskBinding.taskDeadline.getMonth() + 1, dialogueAddTaskBinding.taskDeadline.getDayOfMonth()));

                            toDoDatabase.updateTask(taskItem);
                            updateToDoTaskItem(myPos, taskItem);

                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {

                        }})
                    .setView(dialogueAddTaskBinding.getRoot())
                    .create();
            }
        }
    }
