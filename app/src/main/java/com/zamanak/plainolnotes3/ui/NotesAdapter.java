package com.zamanak.plainolnotes3.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zamanak.plainolnotes3.EditorActivity;
import com.zamanak.plainolnotes3.R;
import com.zamanak.plainolnotes3.database.NoteEntity;
import com.zamanak.plainolnotes3.utilities.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zamanak.plainolnotes3.utilities.Constants.NOT_ID_KEY;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final List<NoteEntity> mNotes;
    private final Context mContext;

    public NotesAdapter(List<NoteEntity> notes, Context mContext) {
        this.mNotes = notes;
        this.mContext = mContext;
    }

    /**
     * this method instance when a new viewHolder called in ViewHolder class
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_list_item,parent,false);
        return new ViewHolder(view);
    }

    /**
     * this method called when we want to update each rows item of the list
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NoteEntity note = mNotes.get(position);
        holder.mTextView.setText(note.getText());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext , EditorActivity.class).putExtra(NOT_ID_KEY,note.getId()));
            }
        });
    }

    /**
     * this method return all the the data in the list
     * @return
     */
    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.note_text)
        TextView mTextView;
        @BindView(R.id.fab)
        FloatingActionButton mFab;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
