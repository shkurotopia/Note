package com.example.note.db;

import android.provider.BaseColumns;

public final class NoteContract {
    private NoteContract() {
    }

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "Note";
        public static final String COLUMN_NAME_TITLE = "TITTLE";
        public static final String COLUMN_NAME_DATE = "CREATION_DATE";
        public static final String COLUMN_NAME_CONTENT = "CONTENT";
    }
}
