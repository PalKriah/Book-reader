package com.learn.bookreader;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.learn.bookreader.model.Book;

public class BookFragment extends Fragment {

    Book book;

    public BookFragment() {
        super(R.layout.book_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        book = (Book) getArguments().getSerializable("book");
        boolean delete = getArguments().getBoolean("delete");
        TextView titleText = view.findViewById(R.id.titleText);

        titleText.setText(book.getTitle());

        Button deleteBookBtn = view.findViewById(R.id.deleteBookBtn);
        Button playEBookBtn = view.findViewById(R.id.playEBookBtn);
        Button playAudioBookBtn = view.findViewById(R.id.playAudioBookBtn);
        if (delete) {
            deleteBookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).deleteBook(book);
                }
            });
        } else {
            deleteBookBtn.setVisibility(View.GONE);
        }


        if (book.isHasEBook()) {
            playEBookBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EBookActivity.class);
                    intent.putExtra("eBookPath", book.getEBookPath());
                    startActivity(intent);
                }
            });
        } else {
            playEBookBtn.setEnabled(false);
            playEBookBtn.setAlpha(0.5f);
        }

        if (book.isHasAudioBook()) {
            playAudioBookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AudioBookActivity.class);
                    intent.putExtra("audioBookPath", book.getAudioBookPath());
                    startActivity(intent);
                }
            });
        } else {
            playAudioBookBtn.setEnabled(false);
            playAudioBookBtn.setAlpha(0.5f);
        }
    }
}
