package com.example.note;

import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.noties.markwon.Markwon;

public class NoteRVAdapter extends RecyclerView.Adapter<NoteRVAdapter.ViewHolder> {
    private final ArrayList<Note> noteArrayList;
    private final Context context;
    final private ListItemClickListener onCLickListener;

    public NoteRVAdapter(ArrayList<Note> noteArrayList, Context context, ListItemClickListener onCLickListener) {
        this.noteArrayList = noteArrayList;
        this.context = context;
        this.onCLickListener = onCLickListener;
    }

    @NonNull
    @Override
    public NoteRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new NoteRVAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull NoteRVAdapter.ViewHolder holder, int position) {
        // on below line we are setting data to our ui components.
        Note modal = noteArrayList.get(position);

        final Markwon markwon = Markwon.create(context);
        final Spanned markdown = markwon.toMarkdown(modal.getNoteContent());

        holder.noteTittle.setText(modal.getNoteTittle());
        holder.noteDate.setText(modal.getNoteDate().toString());
        holder.noteContent.setText(markdown);
    }

    @Override
    public int getItemCount() {
        // returning the size of array list
        return noteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView noteTittle;
        private final TextView noteContent;
        private final TextView noteDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTittle = itemView.findViewById(R.id.idNoteTittle);
            noteContent = itemView.findViewById(R.id.idNoteText);
            noteDate = itemView.findViewById(R.id.idNoteTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            onCLickListener.onListItemClick(position);
        }

    }

    public interface ListItemClickListener
    {
        public void onListItemClick(int pos);
    }
}
