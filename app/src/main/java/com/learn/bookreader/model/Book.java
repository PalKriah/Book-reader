package com.learn.bookreader.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "book")
public class Book implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String release;
    boolean hasAudioBook;
    String audioBookPath;
    boolean hasEBook;
    String eBookPath;

    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public boolean isHasAudioBook() {
        return hasAudioBook;
    }

    public void setHasAudioBook(boolean hasAudioBook) {
        this.hasAudioBook = hasAudioBook;
    }

    public String getAudioBookPath() {
        return audioBookPath;
    }

    public void setAudioBookPath(String audioBookPath) {
        this.audioBookPath = audioBookPath;
    }

    public boolean isHasEBook() {
        return hasEBook;
    }

    public void setHasEBook(boolean hasEBook) {
        this.hasEBook = hasEBook;
    }

    public String getEBookPath() {
        return eBookPath;
    }

    public void setEBookPath(String eBookPath) {
        this.eBookPath = eBookPath;
    }
}
