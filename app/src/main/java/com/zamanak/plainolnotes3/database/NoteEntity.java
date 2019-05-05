package com.zamanak.plainolnotes3.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * this class called model or object in java and it called dataEntity in kotlin
 */

// ***** for converting this pojo class to a data structure class(Room Persistance) -> room library works with annotation
@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true) // this means id can generate automatically
    private int id;
    private Date date; // sqlite can not store pure java so Date must convert to long integer -> please refer to DateConverter class
    private String text;

    @Ignore // this annotation means Room can have only one constructor so this annotation ignore this and other constructor
    public NoteEntity() {
    }

    public NoteEntity(int id, Date date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }

    @Ignore
    public NoteEntity(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * these two methods are for debuging
     */
    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
