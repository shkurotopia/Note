package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note.db.NoteContract;
import com.example.note.db.NoteDbHelper;

import java.time.LocalDate;

public class Editor extends AppCompatActivity {
    private int noteId;
    private NoteDbHelper dbHelper;
    private EditText editTittle, editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();

        dbHelper = new NoteDbHelper(Editor.this);

        noteId = intent.getIntExtra("com.example.note.itemId", 0);

        editTittle = findViewById(R.id.editorHead);
        editTittle.setText(intent.getStringExtra("com.example.note.itemHeader"));

        editContent = findViewById(R.id.editorContent);
        editContent.setText(intent.getStringExtra("com.example.note.itemContent"));
    }

    public void onClickSave(View v)
    {
        if (noteId == 0)
            dbHelper.addNewNote(editTittle.getText().toString(), editContent.getText().toString(), LocalDate.now().toString());
        else
            dbHelper.updateNote(noteId, editTittle.getText().toString(), editContent.getText().toString());
    }
}