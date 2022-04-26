package com.example.note;

import android.content.Context;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.commonmark.node.Node;

import java.util.ArrayList;

import io.noties.markwon.Markwon;
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin;

/**
 * Recycler view adapter for data
 *
 * @author Solovev Alexander
 * @version 1.0
 */
public class NoteRVAdapter extends RecyclerView.Adapter<NoteRVAdapter.ViewHolder> {
    /**
     * Array of notes
     */
    private final ArrayList<Note> noteArrayList;

    private final Context context;
    private final ListItemClickListener onCLickListener;

    /**
     * Markdown parser and renderer
     */
    private final Markwon markwon;

    public NoteRVAdapter(ArrayList<Note> noteArrayList, Context context, ListItemClickListener onCLickListener) {
        this.noteArrayList = noteArrayList;
        this.context = context;
        this.onCLickListener = onCLickListener;

        this.markwon = Markwon.builder(context).
                usePlugin(StrikethroughPlugin.create()).
                build();
    }

    @NonNull
    @Override
    public NoteRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteRVAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false));
    }

    /**
     * Renders Markdown content of noteArrayList into TextView
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull NoteRVAdapter.ViewHolder holder, int position) {
        Note note = noteArrayList.get(position);

        final Node node = markwon.parse(note.getNoteContent());

        final Spanned markdown = markwon.render(node);

        markwon.setParsedMarkdown(holder.noteContent, markdown);

        holder.noteTittle.setText(note.getNoteTittle());
        holder.noteDate.setText(note.getNoteDate().toString());
        holder.noteContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * @return size of noteArrayList
     */
    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(int pos);
    }

    /**
     * Holder for note content
     */
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
        public void onClick(View v) {
            int position = getAdapterPosition();
            onCLickListener.onListItemClick(position);
        }

    }
}
