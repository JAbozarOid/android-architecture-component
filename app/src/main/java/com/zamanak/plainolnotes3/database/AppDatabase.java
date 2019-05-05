package com.zamanak.plainolnotes3.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {NoteEntity.class},version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * this class is a singleton class so we must define some fields
     */
    /**
     * Advantages of persistence of Room Library
     * if device has no network access the data will be available
     * Faster loading of data
     * Can Save a huge amount of network data if the data is static
     */

    public static final String DATABASE_NAME = "AppDatabase.db";

    // this field for support singleton -> volatile which means that it can only be reference from main memory
    private static volatile AppDatabase instance;

    private static final Object LOCK = new Object(); // that is not allowed that two class refer to database at the same time

    // this method is abstract because this method never call directly
    public abstract NoteDAO noteDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
