package com.zamanak.plainolnotes3;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import com.zamanak.plainolnotes3.database.AppDatabase;
import com.zamanak.plainolnotes3.database.NoteDAO;
import com.zamanak.plainolnotes3.database.NoteEntity;
import com.zamanak.plainolnotes3.utilities.SampleData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

// this import is for using Junit
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private NoteDAO mDao;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context,AppDatabase.class).build();
        mDao = mDb.noteDAO();
        Log.i(TAG, "createDb: ");
    }

    @After
    public void closeDb(){
        mDb.close();
        Log.i(TAG, "closeDb: ");
    }

    @Test
    public void createAndRetrieveNotes(){
        mDao.insertAll(SampleData.getNotes());
        int count = mDao.getCount(); // how many rows in the table
        Log.i(TAG, "createAndRetrieveNotes: count=" + count);
        assertEquals(SampleData.getNotes().size() , count);
        Log.i(TAG, "createAndRetrieveNotes: " + SampleData.getNotes());
    }

    @Test
    public void compareStrings(){
        mDao.insertAll(SampleData.getNotes());
        NoteEntity original = SampleData.getNotes().get(0);
        NoteEntity fromDb = mDao.getNoteById(1);
        assertEquals(original.getText() , fromDb.getText());
        Log.i(TAG, "compareStrings: " + fromDb.getText());
    }
}
