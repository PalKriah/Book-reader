package com.learn.bookreader.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.learn.bookreader.model.Book;

import java.util.List;

@Dao
public interface BookDAO {
    @Query("SELECT * FROM book")
    List<Book> getAll();

    @Insert
    void insertBook(Book book);

    @Insert
    void insertAll(Book... book);

    @Update
    public void updateBook(Book book);

    @Delete
    public void deleteBook(Book book);
}
