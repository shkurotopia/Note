package com.example.note.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.note.Note;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.example.note.db.NoteContract.NoteEntry;

public class NoteDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notes.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NoteEntry.COLUMN_NAME_TITLE + " TEXT, " +
                    NoteEntry.COLUMN_NAME_DATE + " TEXT NOT NULL, " +
                    NoteEntry.COLUMN_NAME_CONTENT + " TEXT)";
    private static  final String SQL_INSERT_HELLO_WORLD =
            "INSERT INTO " + NoteEntry.TABLE_NAME + "(" + NoteEntry.COLUMN_NAME_TITLE +
                    ", " + NoteEntry.COLUMN_NAME_DATE + ", " + NoteEntry.COLUMN_NAME_CONTENT + ")" +
                    " VALUES ('Hello, world!', '2022-04-25', '# Возможности\n" +
                    "Заметки в этом приложении можно писать с применением языка разметки Markdown. Примеры разметки можно найти [здесь](https://ru.wikipedia.org/wiki/Markdown).')";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE_NAME;


    public NoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_INSERT_HELLO_WORLD);
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

        db.insert(NoteContract.NoteEntry.TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<Note> readNotes() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                NoteContract.NoteEntry._ID,
                NoteContract.NoteEntry.COLUMN_NAME_TITLE,
                NoteContract.NoteEntry.COLUMN_NAME_DATE,
                NoteContract.NoteEntry.COLUMN_NAME_CONTENT
        };

        Cursor cursor = db.query(
                NoteContract.NoteEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                NoteContract.NoteEntry._ID + " ASC"
        );

        ArrayList<Note> notes = new ArrayList<>();

        while (cursor.moveToNext()) {
            int noteId = cursor.getInt(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry._ID));
            String noteTitle = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_TITLE));
            String noteDate = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_DATE));
            String noteContent = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_CONTENT));

            notes.add(new Note(noteTitle, noteContent, LocalDate.parse(noteDate, DateTimeFormatter.ISO_LOCAL_DATE), noteId));
        }

        cursor.close();
        return notes;
    }

    public void updateNote(int id, String tittle, String content) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NoteContract.NoteEntry.COLUMN_NAME_TITLE, tittle);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_CONTENT, content);

        db.update(NoteContract.NoteEntry.TABLE_NAME, values, "_id=?", new String[]{Integer.toString(id)});
        db.close();
    }
}
