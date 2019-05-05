package com.zamanak.plainolnotes3;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.zamanak.plainolnotes3.database.NoteEntity;
import com.zamanak.plainolnotes3.viewmodel.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zamanak.plainolnotes3.utilities.Constants.EDITING_KEY;
import static com.zamanak.plainolnotes3.utilities.Constants.NOT_ID_KEY;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.note_text)
    TextView mTextView;

    /**
     * each activity or fragment has own viewModel
     * viewModel should register in it's activity
     */
    private EditorViewModel mViewModel;
    private boolean mNewNote , mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check); // change the default back icon to ic_check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        if (savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);// when  the orientation of app has changed the editing is not lost
        }
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);
        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(@Nullable NoteEntity noteEntity) {
                if (noteEntity != null && !mEditing) {
                    mTextView.setText(noteEntity.getText());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(R.string.new_note);
            mNewNote = true;
        } else {
            setTitle(R.string.edit_note);
            int noteId = extras.getInt(NOT_ID_KEY);
            mViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewNote) {
            // if an existing note was available
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // we want when the use check the ic_check the edited not save in database and return to main activity
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            mViewModel.deleteNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // we want when user press back button or ic_check do same action


    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveNote(mTextView.getText().toString());
        finish();
    }

    //*** this method call automatically when the configuration of app change , such as change the orientation of app (landscape or vertical)
    // in this activity when user edit a note and before save the changes when the user change the orientation of app the edited note has lost


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY,true);
        super.onSaveInstanceState(outState);
    }
}
