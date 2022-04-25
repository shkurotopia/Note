package com.example.note;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.note.db.NoteDbHelper;

import java.time.LocalDate;

import io.noties.markwon.Markwon;
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin;
import io.noties.markwon.editor.MarkwonEditor;
import io.noties.markwon.editor.MarkwonEditorTextWatcher;

public class Editor extends AppCompatActivity {
    private int noteId;
    private NoteDbHelper dbHelper;
    private EditText editTittle, editContent;
    private Markwon markwon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        final Markwon markwon = Markwon.builder(Editor.this)
                .usePlugin(StrikethroughPlugin.create())
                .build();
        final MarkwonEditor editor = MarkwonEditor.create(markwon);

        Intent intent = getIntent();

        dbHelper = new NoteDbHelper(Editor.this);

        noteId = intent.getIntExtra("com.example.note.itemId", 0);

        editTittle = findViewById(R.id.editorHead);
        editTittle.setText(intent.getStringExtra("com.example.note.itemHeader"));

        editContent = findViewById(R.id.editorContent);
        editContent.addTextChangedListener(MarkwonEditorTextWatcher.withProcess(editor));
        editContent.setText(intent.getStringExtra("com.example.note.itemContent"));
    }

    public void onClickSave(View v) {
        if (noteId == 0)
            dbHelper.addNewNote(editTittle.getText().toString(), editContent.getText().toString(), LocalDate.now().toString());
        else
            dbHelper.updateNote(noteId, editTittle.getText().toString(), editContent.getText().toString());
        finish();

    }
}