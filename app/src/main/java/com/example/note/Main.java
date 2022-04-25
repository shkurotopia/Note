package com.example.note;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.db.NoteDbHelper;

import java.util.ArrayList;

public class Main extends AppCompatActivity implements NoteRVAdapter.ListItemClickListener {
    RecyclerView recyclerView;
    private ArrayList<Note> notes;
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
    protected void onPause() {
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

    public void onClickAdd(View view) {
        Intent intent = new Intent(this, Editor.class);
        intent.putExtra("com.example.note.itemId", 0);
        intent.putExtra("com.example.note.itemHeader", "");
        intent.putExtra("com.example.note.itemContent", "");
        startActivity(intent);
    }

    @Override
    public void onListItemClick(int pos) {
        Intent intent = new Intent(this, Editor.class);
        intent.putExtra("com.example.note.itemId", notes.get(pos).getId());
        intent.putExtra("com.example.note.itemHeader", notes.get(pos).getNoteTittle());
        intent.putExtra("com.example.note.itemContent", notes.get(pos).getNoteContent());
        startActivity(intent);
    }


}