package com.learn.bookreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.learn.bookreader.dao.BookDatabase;
import com.learn.bookreader.model.Book;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BookAdapter.ItemClickListener {

    public static final int ADD_BOOK_CODE = 1;
    public static final int SETTINGS = 2;
    public BookDatabase db;
    BookAdapter adapter;
    List<Book> books;
    RecyclerView recyclerView;
    MainActivity activity;

    public static final String PREFERENCES = "preferences";
    public static final String LANGUAGE = "language";
    public static final String DELETE = "delete";
    boolean delete;
    String language;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferences();
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        activity = this;
        db = Room.databaseBuilder(this, BookDatabase.class, "book_database").build();

        recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getBooks();
        wireMenu();
    }

    @Override
    public void onItemClick(View view, int position) {
        changeBookFragment(books.get(position));
    }

    private void setAdapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void wireMenu() {
        ImageView addBtn = findViewById(R.id.addBtn);
        ImageView optionsBtn = findViewById(R.id.optionsBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivityForResult(intent, ADD_BOOK_CODE);
            }
        });

        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra("language", language);
                intent.putExtra("delete", delete);
                startActivityForResult(intent, SETTINGS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            String title = data.getStringExtra("title");
            String eBookPath = data.getStringExtra("eBookPath");
            String audioBookPath = data.getStringExtra("audioBookPath");
            Book book = new Book();
            book.setTitle(title);
            book.setEBookPath(eBookPath);
            book.setAudioBookPath(audioBookPath);
            if (eBookPath.equals("")) {
                book.setHasEBook(false);
            } else {
                book.setHasEBook(true);
            }

            if (audioBookPath.equals("")) {
                book.setHasAudioBook(false);
            } else {
                book.setHasAudioBook(true);
            }

            adapter.notifyDataSetChanged();

            addBook(book);
        } else if (resultCode == 2) {
            setSettings();
        }
        if (books.size() > 0) {
            changeBookFragment(books.get(0));
        }
    }

    private void changeBookFragment(Book book) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        Fragment newFragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        bundle.putBoolean("delete", delete);
        newFragment.setArguments(bundle);

        ft.replace(R.id.bookFragment, newFragment, "bookFragment");
        ft.commit();
    }

    private void getBooks() {
        Thread newThread = new Thread(new Runnable() {
            @SuppressLint("ResourceType")
            @Override
            public void run() {
                books = db.bookDao().getAll();
                adapter = new BookAdapter(activity, books);
                adapter.setClickListener((BookAdapter.ItemClickListener) activity);
                setAdapter();
                if (books.size() > 0) {
                    changeBookFragment(books.get(0));
                }
            }
        });
        newThread.start();
    }

    public void addBook(Book book) {
        new Thread((new Runnable() {
            Book book;

            public void run() {
                db.bookDao().insertBook(book);
                getBooks();
            }

            public Runnable pass(Book book) {
                this.book = book;
                return this;
            }
        }).pass(book)).start();
    }

    public void deleteBook(Book book) {
        new Thread((new Runnable() {
            Book book;

            public void run() {
                db.bookDao().deleteBook(book);
                getBooks();
                if (books.size() > 0) {
                    changeBookFragment(books.get(0));
                }
            }

            public Runnable pass(Book book) {
                this.book = book;
                return this;
            }
        }).pass(book)).start();
    }

    private void setPreferences() {
        sharedpreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        delete = sharedpreferences.getBoolean(DELETE, false);
        language = sharedpreferences.getString(LANGUAGE, "en");

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public void setSettings() {
        if (!language.equals(sharedpreferences.getString(LANGUAGE, ""))) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            activity.recreate();
        }
        delete = sharedpreferences.getBoolean(DELETE, false);
    }
}