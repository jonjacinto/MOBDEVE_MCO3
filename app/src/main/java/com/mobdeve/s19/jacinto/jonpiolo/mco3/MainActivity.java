package com.mobdeve.s19.jacinto.jonpiolo.mco3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobdeve.s19.jacinto.jonpiolo.mco3.databinding.ActivityMainBinding;
import com.mobdeve.s19.jacinto.jonpiolo.mco3.databinding.DialogueAddTaskBinding;

import java.time.LocalDate;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.binding.fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialogue().show();
            }
        });

        ToDoDatabase toDoDatabase =new ToDoDatabase(getApplicationContext());
        taskAdapter =new TaskAdapter(toDoDatabase.getToDoList(), this);
        binding.tasklist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.tasklist.setAdapter(taskAdapter);

    }
    private Dialog showCustomDialogue(){
        DialogueAddTaskBinding dialogueAddTaskBinding = DialogueAddTaskBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(this)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        ToDoDatabase toDoDatabase = new ToDoDatabase(getApplicationContext());

                        toDoDatabase.addTask(new ToDoTaskItem(
                            dialogueAddTaskBinding.taskAddTitle.getText().toString(),
                                dialogueAddTaskBinding.taskAddTag.getText().toString(),
                                LocalDate.of(dialogueAddTaskBinding.taskDeadline.getYear(), dialogueAddTaskBinding.taskDeadline.getMonth() + 1, dialogueAddTaskBinding.taskDeadline.getDayOfMonth())
                        ));
                        binding.tasklist.smoothScrollToPosition(taskAdapter.getItemCount() - 1);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setView(dialogueAddTaskBinding.getRoot())
                .create();

    }
}