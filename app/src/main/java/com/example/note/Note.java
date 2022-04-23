package com.example.note;

import java.time.LocalDate;

public class Note {
    private String noteTittle;
    private String noteContent;
    private LocalDate noteDate;

    public Note(String noteTittle, String noteContent, LocalDate noteDate) {
        this.noteTittle = noteTittle;
        this.noteContent = noteContent;
        this.noteDate = noteDate;
    }

    public String getNoteTittle() {
        return noteTittle;
    }

    public void setNoteTittle(String noteTittle) {
        this.noteTittle = noteTittle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public LocalDate getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(LocalDate noteDate) {
        this.noteDate = noteDate;
    }
}
