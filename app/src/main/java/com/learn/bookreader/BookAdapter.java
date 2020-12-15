package com.learn.bookreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.bookreader.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Book> books;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    BookAdapter(Context context, List<Book> books) {
        this.mInflater = LayoutInflater.from(context);
        this.books = books;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.book_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.title.setText(book.getTitle());
        if (book.isHasAudioBook()) {
            holder.hasAudioBook.setBackground(ContextCompat.getDrawable(context, android.R.drawable.checkbox_on_background));
        }
        if (book.isHasEBook()) {
            holder.hasEBook.setBackground(ContextCompat.getDrawable(context, android.R.drawable.checkbox_on_background));
        }
        holder.image.setImageResource(R.drawable.book);
    }

    @Override
    public int getItemCount() {
        if (books != null) {
            return books.size();

        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView hasEBook;
        ImageView hasAudioBook;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            hasEBook = itemView.findViewById(R.id.hasEBook);
            hasAudioBook = itemView.findViewById(R.id.hasAudioBook);
            image = itemView.findViewById(R.id.bookImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    Book getItem(int id) {
        return books.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
