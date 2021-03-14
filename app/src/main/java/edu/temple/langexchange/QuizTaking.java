package edu.temple.langexchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuizTaking extends AppCompatActivity {

    TextView answer;
    Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_taking);
        getSupportActionBar().setTitle(R.string.quiz_title);

        answer = findViewById(R.id.editAnswer);
        submit = findViewById(R.id.quizAnswerSubmitBtn);

        Intent intent = getIntent();

        int i = intent.getIntExtra("position", 0);
        int length = intent.getIntExtra("quizLength", 0);
        System.out.println("Received size: " + length);
        System.out.println("Received index: " + i);
        ArrayList<String> answerQuiz = new ArrayList<>(length);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerQuiz.add(i, answer.getText().toString().toUpperCase());
                answer.setText("");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("QuizAnswer", answerQuiz);

                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}
