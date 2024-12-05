package com.mobdeve.s19.jacinto.jonpiolo.mco3;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

public class ToDoDatabase {
    private DatabaseHandler dbHandler;

    public ToDoDatabase(Context context){
        this.dbHandler = new DatabaseHandler(context);
    }

    public int addTask(ToDoTaskItem task){
        SQLiteDatabase db =dbHandler.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHandler.TODO_TITLE, task.getTitle());
        cv.put(DatabaseHandler.TODO_TAG, task.getTaskTag());
        cv.put(DatabaseHandler.TODO_DEADLINE, task.getTaskDeadline().toString());

        int _id =(int) db.insert(DatabaseHandler.TODO_TABLE, null, cv);

        db.close();

        return _id;
    }
    public void updateTask(ToDoTaskItem task) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHandler.TODO_TITLE, task.getTitle());
        cv.put(DatabaseHandler.TODO_TAG, task.getTaskTag());
        cv.put(DatabaseHandler.TODO_DEADLINE, task.getTaskDeadline().toString());

        db.update(DatabaseHandler.TODO_TABLE,
                cv,
                DatabaseHandler.TODO_ID + " = ?",
                new String[]{String.valueOf(task.getTaskId())});
        db.close();


    }

    public void deleteTask(ToDoTaskItem task) {
        SQLiteDatabase db =dbHandler.getWritableDatabase();
        db.delete(DatabaseHandler.TODO_TABLE,
                DatabaseHandler.TODO_ID + " = ?",
                new String[]{String.valueOf(task.getTaskId())});
        db.close();
    }

    public ArrayList<ToDoTaskItem> getToDoList() {
        ArrayList<ToDoTaskItem> result = new ArrayList<ToDoTaskItem>();
        SQLiteDatabase db =dbHandler.getReadableDatabase();
        Cursor c = db.query(
                DatabaseHandler.TODO_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseHandler.TODO_ID + " ASC",
                null

        );
        while(c.moveToNext()){
                result.add(new ToDoTaskItem(
                        c.getInt(c.getColumnIndexOrThrow(DatabaseHandler.TODO_ID)),
                        c.getString(c.getColumnIndexOrThrow(DatabaseHandler.TODO_TITLE)),
                        c.getString(c.getColumnIndexOrThrow(DatabaseHandler.TODO_TAG)),
                        LocalDate.parse(c.getString(c.getColumnIndexOrThrow(DatabaseHandler.TODO_DEADLINE)))
                ));
        }
        c.close();
        db.close();

        return result;

    }
}
