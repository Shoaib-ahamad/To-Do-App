package com.example.to_dolistapp.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.to_dolistapp.Mode.Todomode;

import java.util.ArrayList;
import java.util.List;

public class databasee extends SQLiteOpenHelper {
    private static final String Database_Name = "database";
    private static final String Table_Name = "to_do_table";
    private static final String col_1 = "ID";  // Updated to match the column name in the table
    private static final String col_2 = "task";
    private static final String col_3 = "status";
    private static final int Database_v = 1;

    public databasee(Context context) {
        super(context, Database_Name, null, Database_v);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_Name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, status INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    public void insertTask(Todomode mode) {
        SQLiteDatabase dd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_2, mode.getTask());
        cv.put(col_3, 0); // Default status as 0
        dd.insert(Table_Name, null, cv);
    }

    public void updateTask(int id, String task) {
        SQLiteDatabase dd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_2, task);
        dd.update(Table_Name, cv, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status) {
        SQLiteDatabase dd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_3, status);
        dd.update(Table_Name, cv, "ID=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        SQLiteDatabase dd = this.getWritableDatabase();
        dd.delete(Table_Name, "ID=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<Todomode> getAllTask() {
        SQLiteDatabase dd = this.getReadableDatabase();
        Cursor c = null;
        List<Todomode> modelList = new ArrayList<>();
        dd.beginTransaction();
        try {
            c = dd.query(Table_Name, null, null, null, null, null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    Todomode m = new Todomode();
                    m.setId(c.getInt(c.getColumnIndexOrThrow(col_1))); // Fixed column name
                    m.setTask(c.getString(c.getColumnIndexOrThrow(col_2)));
                    m.setStatus(c.getInt(c.getColumnIndexOrThrow(col_3)));
                    modelList.add(m);
                } while (c.moveToNext());
            }
        } finally {
            if (c != null) {
                c.close();
            }
            dd.endTransaction();
        }
        return modelList;
    }
}
