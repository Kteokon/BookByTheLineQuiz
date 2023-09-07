package com.example.bookbythelinequiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    ArrayList<Book> books;
    ArrayList<String> coversNames;
    ArrayList<String> usersAnswers = new ArrayList<>();
    int answeredQuestions, rightAnswers;

    TextView firstLine;
    Button startOverButton;
    List<ImageView> covers;
    LinearLayout llCovers, llTitles;
    RecyclerView answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        firstLine = findViewById(R.id.firstLine);
        startOverButton = findViewById(R.id.startOver);
        ImageView cover1 = findViewById(R.id.cover1);
        ImageView cover2 = findViewById(R.id.cover2);
        ImageView cover3 = findViewById(R.id.cover3);
        covers = Arrays.asList(cover1, cover2, cover3);
        llCovers = findViewById(R.id.covers);
        llTitles = findViewById(R.id.titles);
        answers = findViewById(R.id.answers);
        answers.setLayoutManager(new LinearLayoutManager(this));

        for (ImageView cover: covers) {
            cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answeredQuestions++;
                    usersAnswers.add((String) v.getTag());
                    if (v.getTag().equals(firstLine.getTag())) {
                        rightAnswers++;
                    }
                    if (answeredQuestions == books.size()) {
                        llCovers.setVisibility(View.GONE);
                        startOverButton.setVisibility(View.VISIBLE);
                        llTitles.setVisibility(View.VISIBLE);
                        answers.setVisibility(View.VISIBLE);
                        firstLine.setText(rightAnswers + " out of " + books.size() + " are correct");
                        AnswersListAdapter adapter = new AnswersListAdapter(getApplicationContext(), books, usersAnswers);
                        answers.setAdapter(adapter);
                    }
                    else {
                        setCovers();
                    }
                }
            });
        }

        startOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOver();
            }
        });

        books = new ArrayList<>();
        InputStream stream = getResources().openRawResource(R.raw.books);
        InputStreamReader reader = new InputStreamReader(stream);
        Gson gson = new Gson();
        books = gson.fromJson(reader, new TypeToken<List<Book>>(){}.getType());

        startOver();
    }

    private void startOver() {
        answeredQuestions = 0;
        rightAnswers = 0;
        usersAnswers.clear();

        Collections.shuffle(books);
        coversNames = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            coversNames.add(books.get(i).name);
        }
        llCovers.setVisibility(View.VISIBLE);
        startOverButton.setVisibility(View.GONE);
        llTitles.setVisibility(View.GONE);
        answers.setVisibility(View.GONE);
        setCovers();
    }

    private void setCovers() {
        Book book = books.get(answeredQuestions);
        firstLine.setText(book.firstLine);
        firstLine.setTag(book.name);
        List<String> chosenCovers = new ArrayList<>();
        chosenCovers.add(book.name);
        while (chosenCovers.size() < 3) {
            int randomCover = (int) (Math.random() * books.size());
            String cover = books.get(randomCover).name;
            if (!(chosenCovers.contains(cover))) {
                chosenCovers.add(cover);
            }
        }
        Collections.shuffle(chosenCovers);
        for (int i = 0; i < 3; i++) {
            String drawable = chosenCovers.get(i);
            int resId = getResources().getIdentifier(drawable, "drawable", getPackageName());
            covers.get(i).setImageResource(resId);
            covers.get(i).setTag(drawable);

        }
    }
}