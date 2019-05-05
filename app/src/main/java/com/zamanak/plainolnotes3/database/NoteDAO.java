package com.zamanak.plainolnotes3.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;

// ***** DAO or data access object is an interface in Room library

/**
 * DAO means :
 * This is how we access our database
 * Use simple annotations
 * Here we define the functionality of our database
 */
@Dao
public interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntity noteEntity);

    // a list of notes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteEntity> notes);

    @Delete
    void deleteNote(NoteEntity noteEntity);

    // now we need some queries -> notes is table name
    @Query("SELECT * FROM notes WHERE id = :id")
    NoteEntity getNoteById(int id);

    /**
     * A live data object can publish changes and then an activity or a fragment can subscribe to observe those changes and react whenever the data
     * needs to be updated visually.
     * @return
     */
    // this query return newest notes that sorted by dates
    @Query("SELECT * FROM notes ORDER BY date DESC")
    LiveData<List<NoteEntity>> getAll();

    @Query("DELETE FROM notes")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM notes")
    int getCount();
}
