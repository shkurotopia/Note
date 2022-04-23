package com.example.note;

import java.time.LocalDate;

public class Note {
    private String noteTittle;
    private String noteContent;
    private LocalDate noteDate;
    private int id;

    public Note(String noteTittle, String noteContent, LocalDate noteDate, int id) {
        this.noteTittle = noteTittle;
        this.noteContent = noteContent;
        this.noteDate = noteDate;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
