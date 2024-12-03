package com.mobdeve.s19.jacinto.jonpiolo.mco3;
import java.time.LocalDate;
public class ToDoTaskItem {
    private static final int DEFAULT_ID = -1;

    private int taskId;
    private String title;

    public String getTaskTag() {
        return taskTag;
    }

    public void setTaskTag(String taskTag) {
        this.taskTag = taskTag;
    }
    private LocalDate taskDeadline;
    private String taskTag;

    public ToDoTaskItem(int taskId, String title, String taskTag, LocalDate taskDeadline) {
        this.taskId = taskId;
        this.title = title;
        this.taskTag = taskTag;
        this.taskDeadline = taskDeadline;
    }

    public ToDoTaskItem(String title, String taskTag, LocalDate taskDeadline) {
        this(DEFAULT_ID, title, taskTag, taskDeadline);
    }

    public ToDoTaskItem() {
        this(DEFAULT_ID, "Blank", "N/A", LocalDate.parse("1970-01-01"));
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getTaskDeadline(){
        return taskDeadline;
    }

    public void setTaskDeadline(LocalDate taskDeadline){
        this.taskDeadline = taskDeadline;
    }

}
