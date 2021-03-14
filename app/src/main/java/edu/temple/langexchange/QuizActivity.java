package edu.temple.langexchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity {
    Button button;
    ArrayList<String> res, correctAnswer = new ArrayList<>();
    ArrayList<Flashcards> test = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_quiz);
        getSupportActionBar().setTitle(R.string.quiz_title);
        

        //test = new Flashcards[5];
        test.add(new Flashcards(1, "Hi", "Hola", "Expression with which you greet"));
        test.add(new Flashcards(2, "One", "Uno", "Number One"));
        test.add(new Flashcards(3, "Two", "Dos", "Number Two"));
        test.add(new Flashcards(4, "Three", "Tres", "Number Three"));
        test.add(new Flashcards(5, "Four", "Cuatro", "Number Four"));

//        FlashcardAdapter adapter = new FlashcardAdapter(this, test);
        ListView list = findViewById(R.id.quizDisplay);
        button = findViewById(R.id.quizDisplayBtn);

        ArrayList<String> questions = new ArrayList<>();
        for(Flashcards card : test)
        {
            questions.add(card.definition + " is?");
            correctAnswer.add(card.originalWord.toUpperCase());
        }

        QuizAdapter adapter = new QuizAdapter(this, questions);
        list.setAdapter(adapter);

        int quizLength = correctAnswer.size();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuizActivity.this, QuizTaking.class);
                intent.putExtra("position", position);
                intent.putExtra("quizLength", quizLength);
                System.out.println("size of array:" + correctAnswer.size());
                startActivityForResult(intent, 1);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && requestCode == RESULT_OK){
            res = data.getStringArrayListExtra("QuizAnswer");
            res.removeAll(correctAnswer);
            System.out.println(res);
        }
    }
}
