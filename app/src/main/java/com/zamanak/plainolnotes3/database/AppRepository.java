package com.zamanak.plainolnotes3.database;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.zamanak.plainolnotes3.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repository class (local or remote data) can encapsulate the logic you need to decide where the data will come from
 * Repository class should be a Singleton class
 * in this class we want to store data reference in the repository
 */

public class AppRepository {

    //private static final AppRepository ourInstance = new AppRepository(); // instantiated of a class

    // "*in order to pass context to room library ourInstance must changed*"
    private static AppRepository ourInstance;
    public LiveData<List<NoteEntity>> mNotes; // here just declare it and in the constructor we instantiated
    private AppDatabase mDb; // in order to access to db we need access to context so we must refactor some class and pass context to room library -> "*so ourInstance must change*"

    /**
     * for handle room database operation execute in background thread
     * it is for making sure that "not run" multiple database operation at the same time
     * by this executor we sure all execute run after other
     */
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null){
            ourInstance = new AppRepository(context); // so we pass Context as a parameter to AppRepository method
        }
        return ourInstance;
    }

    // constructor
    private AppRepository(Context context) {
        // *** the order of this codes are important
        mDb = AppDatabase.getInstance(context); // now we have a reference to actual database and now ready to insert the data
        mNotes = getAllNotes();
    }

    public void addSampleData() {
        // we need reference to database so we must access to AppDatabase class
        // *** before add or insert some data to database we need run operation in background thread
        // *** -> all Room database operation must be execute in the background thread -> so we must create a executor object up here
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDAO().insertAll(SampleData.getNotes());
            }
        });
    }

    // *** this method is for AppRepository to know where the data come from, from local or remote
    // *** this method return all data in table notes
    private LiveData<List<NoteEntity>> getAllNotes(){
        return mDb.noteDAO().getAll();
    }

    public void deleteAllNotes() {
        // because deleteAll method in NoteDao return int we must handle background thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDAO().deleteAll(); // when this method call we do not need change anything in the main activity because the observer handle it
            }
        });
    }

    public NoteEntity getNoteById(int noteId) {
        return mDb.noteDAO().getNoteById(noteId);
    }

    public void insertNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDAO().insertNote(note);
            }
        });
    }

    public void deleteNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDAO().deleteNote(note);
            }
        });

    }
}
