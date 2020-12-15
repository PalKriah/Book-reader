package com.learn.bookreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddBookActivity extends AppCompatActivity {
    public static final int PICK_EBOOK_RESULT_CODE = 1;
    public static final int PICK_AUDIOBOOK_RESULT_CODE = 2;

    private TextView eBookPathView;
    private TextView audioBookPathView;

    private String eBookPath;
    private String audioBookPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_book_layout);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_back));
        Button selectEBook = findViewById(R.id.selectEbook);
        Button selectAudioBook = findViewById(R.id.selectAudioBook);
        eBookPathView = findViewById(R.id.eBookPath);
        audioBookPathView = findViewById(R.id.audioBookPath);

        eBookPath = "";
        audioBookPath = "";

        selectEBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICK_EBOOK_RESULT_CODE);
            }
        });

        selectAudioBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICK_AUDIOBOOK_RESULT_CODE);
            }
        });

        wireMenu();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_EBOOK_RESULT_CODE) {
            Uri uri = data.getData();
            if (uri != null) {
                eBookPath = uri.getPath();
                eBookPathView.setText(eBookPath);
            }
        } else if (requestCode == PICK_AUDIOBOOK_RESULT_CODE) {
            Uri uri = data.getData();
            if (uri != null) {
                audioBookPath = uri.getPath();
                audioBookPathView.setText(audioBookPath);
            }
        }
    }

    public void addBook(View view) {
        EditText editBookTitle = findViewById(R.id.editBookTitle);

        Intent intent = new Intent();
        intent.putExtra("title", editBookTitle.getText().toString());
        intent.putExtra("audioBookPath", audioBookPath);
        intent.putExtra("eBookPath", eBookPath);
        setResult(1, intent);
        finish();
    }

    public void wireMenu() {
        Button addBtn = findViewById(R.id.backBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
