package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Editor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();

        TextView textView = findViewById(R.id.editorHead);
        textView.setText(intent.getStringExtra("com.example.note.itemHeader"));

        textView = findViewById(R.id.editorContent);
        textView.setText(intent.getStringExtra("com.example.note.itemContent"));
    }

    public void onClickSave(View v)
    {
        Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();
    }
}