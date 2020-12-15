package com.learn.bookreader.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.learn.bookreader.model.Book;

@Database(entities = Book.class, version = 1)
public abstract class BookDatabase extends RoomDatabase {
    public abstract BookDAO bookDao();
}
