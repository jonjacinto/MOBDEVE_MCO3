package com.mobdeve.s19.jacinto.jonpiolo.mco3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDoDatabase";
    public static final String TODO_TABLE = "todo_table";

    public static final String TODO_ID = "id";
    public static final String TODO_TITLE = "todo_title";
    public static final String TODO_TAG = "todo_tag";
    public static final String TODO_DEADLINE = "todo_deadline";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TODO_TABLE = "CREATE TABLE IF NOT EXISTS " + TODO_TABLE + " (" +
                TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TODO_TITLE + " TEXT, " +
                TODO_TAG + " TEXT," +
                TODO_DEADLINE + " TEXT)"; //we use TEXT because SQLite accepts date in TEXT if it's in ISO8601 format.
        sqLiteDatabase.execSQL(CREATE_TODO_TABLE);

        String INSERT_DUMMY_TASKS = "INSERT INTO " + TODO_TABLE + " ("+
            TODO_TITLE + ", " + TODO_TAG + ", " + TODO_DEADLINE + ")"+
                "VALUES ('Finish MOBDEVE MCO3', 'Majors', '2024-12-05'), "+
                "('Finish GELITPH Paper', 'GEs', '2024-12-05'), " +
                "('Do grocery', 'Other', '2024-12-05')";


        sqLiteDatabase.execSQL(INSERT_DUMMY_TASKS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(sqLiteDatabase);
    }
}
