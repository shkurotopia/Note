package com.example.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.note.db.NoteContract;
import com.example.note.db.NoteDbHelper;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main extends AppCompatActivity implements NoteRVAdapter.ListItemClickListener {
    private ArrayList<Note> notes;
    RecyclerView recyclerView;
    private NoteDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new NoteDbHelper(Main.this);

        recyclerView = findViewById(R.id.idNotes);

        notes = dbHelper.readNotes();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteRVAdapter adapter = new NoteRVAdapter(notes, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();

        notes.clear();
        notes = dbHelper.readNotes();

        NoteRVAdapter adapter = new NoteRVAdapter(notes, this, this);
        recyclerView.setAdapter(adapter);
    }

    public void onClickAdd(View view)
    {
        Intent intent = new Intent(this, Editor.class);
        intent.putExtra("com.example.note.itemId", 0);
        intent.putExtra("com.example.note.itemHeader", "");
        intent.putExtra("com.example.note.itemContent", "");
        startActivity(intent);
    }
    @Override
    public void onListItemClick(int pos)
    {
        Intent intent = new Intent(this, Editor.class);
        intent.putExtra("com.example.note.itemId", notes.get(pos).getId());
        intent.putExtra("com.example.note.itemHeader", notes.get(pos).getNoteTittle());
        intent.putExtra("com.example.note.itemContent", notes.get(pos).getNoteContent());
        startActivity(intent);
    }
}