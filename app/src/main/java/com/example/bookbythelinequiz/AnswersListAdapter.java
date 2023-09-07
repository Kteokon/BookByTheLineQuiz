package com.example.bookbythelinequiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnswersListAdapter extends RecyclerView.Adapter<AnswersListAdapter.CustomViewHolder>{
    private List<Book> books;
    private List<String> usersAnswers;
    Context context;

    public AnswersListAdapter(Context context, List<Book> books, List<String> usersAnswers) {
        this.books = books;
        this.usersAnswers = usersAnswers;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.answer_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Book book = this.books.get(position);
        String usersAnswer = this.usersAnswers.get(position);
        holder.firstLineTV.setText(book.firstLine);
        int resId = this.context.getResources().getIdentifier(book.name, "drawable", this.context.getPackageName());
        holder.rightAnswer.setImageResource(resId);
        resId = this.context.getResources().getIdentifier(usersAnswer, "drawable", this.context.getPackageName());
        holder.usersAnswer.setImageResource(resId);
        if (book.name.equals(usersAnswer)) {
            holder.ll.setBackgroundResource(R.drawable.right);
        }
        else {
            holder.ll.setBackgroundResource(R.drawable.wrong);
        }
    }

    @Override
    public int getItemCount() {
        return this.usersAnswers.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView firstLineTV;
        ImageView rightAnswer, usersAnswer;
        LinearLayout ll;

        public CustomViewHolder(View view) {
            super(view);

            firstLineTV = view.findViewById(R.id.firstLineTV);
            rightAnswer = view.findViewById(R.id.rightAnswer);
            usersAnswer = view.findViewById(R.id.usersAnswer);
            ll = view.findViewById(R.id.answerLL);
        }
    }
}
