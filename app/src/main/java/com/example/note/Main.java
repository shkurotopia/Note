package com.example.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.note.db.NoteContract;
import com.example.note.db.NoteDbHelper;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main extends AppCompatActivity implements NoteRVAdapter.ListItemClickListener {
    private ArrayList<Note> notes;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.idNotes);

        NoteDbHelper dbHelper = new NoteDbHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
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


        notes = new ArrayList<>();
        while(cursor.moveToNext())
        {
            String noteTitle = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_TITLE));
            String noteDate = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_DATE));
            String noteContent = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_CONTENT));

            notes.add(new Note(noteTitle, noteContent, LocalDate.parse(noteDate)));
        }

        cursor.close();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteRVAdapter adapter = new NoteRVAdapter(notes, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onListItemClick(int pos)
    {
        Intent intent = new Intent(this, Editor.class);
        intent.putExtra("com.example.note.itemId", pos);
        intent.putExtra("com.example.note.itemHeader", notes.get(pos).getNoteTittle());
        intent.putExtra("com.example.note.itemContent", notes.get(pos).getNoteContent());
        startActivity(intent);
    }
}