package com.zamanak.plainolnotes3.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zamanak.plainolnotes3.database.AppRepository;
import com.zamanak.plainolnotes3.database.NoteEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {

    /**
     * Mutable live data has methods that let you change it's value at run time.
     */
    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();

    // we need to retrieve the data so we need the Repository class
    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = mRepository.getNoteById(noteId);
                // after that the data back we will post value
                mLiveNote.postValue(note); // when postValue method called the onChange method of the observer call and return the value
            }
        });

    }

    public void saveNote(String noteText) {
        NoteEntity note = mLiveNote.getValue();
        if (note == null){
            // this is new note
            if (TextUtils.isEmpty(noteText.trim())){
                // the above code means if the user type nothing or press space nothing save in database
                return;
            }else{
                note = new NoteEntity(new Date(),noteText.trim());
            }
        }else{
            // this is an existing note
            note.setText(noteText.trim());
        }
        mRepository.insertNote(note);
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
