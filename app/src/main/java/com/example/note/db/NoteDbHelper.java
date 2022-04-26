package com.example.note.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.note.Note;
import com.example.note.db.NoteContract.NoteEntry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Class that works with database
 *
 * @author Solovev Alexander
 * @version 1.0
 */
public class NoteDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notes.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NoteEntry.COLUMN_NAME_TITLE + " TEXT, " +
                    NoteEntry.COLUMN_NAME_DATE + " TEXT NOT NULL, " +
                    NoteEntry.COLUMN_NAME_CONTENT + " TEXT)";

    private static final String SQL_INSERT_HELLO_WORLD =
            "INSERT INTO " + NoteEntry.TABLE_NAME + "(" + NoteEntry.COLUMN_NAME_TITLE +
                    ", " + NoteEntry.COLUMN_NAME_DATE + ", " + NoteEntry.COLUMN_NAME_CONTENT + ")" +
                    " VALUES ('Hello, world!', '2022-04-25', '# Возможности\n" +
                    "Заметки в этом приложении можно писать с применением языка разметки Markdown. Примеры разметки можно найти [здесь](https://ru.wikipedia.org/wiki/Markdown).')";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;


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

    /**
     * Insert new data into database
     * @param tittle note tittle
     * @param content note contents
     * @param date note creation date
     */
    public void addNewNote(String tittle, String content, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NoteEntry.COLUMN_NAME_TITLE, tittle);
        values.put(NoteEntry.COLUMN_NAME_DATE, date);
        values.put(NoteEntry.COLUMN_NAME_CONTENT, content);

        db.insert(NoteEntry.TABLE_NAME, null, values);

        db.close();
    }

    /**
     * Select data from database
     * @return returns ArrayList of Notes
     */
    public ArrayList<Note> readNotes() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                NoteEntry._ID,
                NoteEntry.COLUMN_NAME_TITLE,
                NoteEntry.COLUMN_NAME_DATE,
                NoteEntry.COLUMN_NAME_CONTENT
        };

        Cursor cursor = db.query(
                NoteEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                NoteEntry._ID + " ASC"
        );

        ArrayList<Note> notes = new ArrayList<>();

        while (cursor.moveToNext()) {
            int noteId = cursor.getInt(cursor.getColumnIndexOrThrow(NoteEntry._ID));
            String noteTitle = cursor.getString(cursor.getColumnIndexOrThrow(NoteEntry.COLUMN_NAME_TITLE));
            String noteDate = cursor.getString(cursor.getColumnIndexOrThrow(NoteEntry.COLUMN_NAME_DATE));
            String noteContent = cursor.getString(cursor.getColumnIndexOrThrow(NoteEntry.COLUMN_NAME_CONTENT));

            notes.add(new Note(noteTitle, noteContent, LocalDate.parse(noteDate, DateTimeFormatter.ISO_LOCAL_DATE), noteId));
        }

        cursor.close();
        return notes;
    }

    /**
     * Update data in database
     * @param id note id
     * @param tittle note tittle
     * @param content note content
     */
    public void updateNote(int id, String tittle, String content) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NoteEntry.COLUMN_NAME_TITLE, tittle);
        values.put(NoteEntry.COLUMN_NAME_CONTENT, content);

        db.update(NoteEntry.TABLE_NAME, values, "_id=?", new String[]{Integer.toString(id)});
        db.close();
    }

    /**
     * Delete data from database
     * @param id note id
     */
    public void deleteNote(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(NoteEntry.TABLE_NAME, "_id=?", new String[]{Integer.toString(id)});

        db.close();
    }
}
