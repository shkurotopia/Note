package com.example.note.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;

public class NoteDbHelper extends SQLiteOpenHelper {

    public static  final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notes.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteContract.NoteEntry.TABLE_NAME + " (" +
                    NoteContract.NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NoteContract.NoteEntry.COLUMN_NAME_TITLE + " TEXT, " +
                    NoteContract.NoteEntry.COLUMN_NAME_DATE + " TEXT NOT NULL, " +
                    NoteContract.NoteEntry.COLUMN_NAME_CONTENT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE_NAME;


    public NoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addNewNote(String tittle, String content, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NoteContract.NoteEntry.COLUMN_NAME_TITLE, tittle);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_DATE, date);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_CONTENT, content);

        db.insert(DATABASE_NAME, null, values);

        db.close();
    }
}
