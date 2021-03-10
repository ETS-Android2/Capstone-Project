package edu.temple.langexchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FlashcardActivity extends AppCompatActivity {

    TextView text;
    GridView gridView;

    Button makeFlashcardBtn;
    Button makeQuizBtn;

    ArrayList<String> flashcards;
    ArrayList<String> translation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        getSupportActionBar().setTitle(R.string.flashcard_title);

        text = findViewById(R.id.flashcardText);
        gridView = findViewById(R.id.flashcardGrid);

        makeFlashcardBtn = findViewById(R.id.makeFlashcardBtn);
        makeQuizBtn = findViewById(R.id.makeQuizBtn);

        text.setText(R.string.flashcard_instructions);

        flashcards = new ArrayList<String>();
        flashcards.add("Example 1");
        flashcards.add("Example 2");
        flashcards.add("Example 3");
        flashcards.add("Example 4");
        flashcards.add("Example 5");

        translation = new ArrayList<String>();
        translation.add("Translation 1");
        translation.add("Translation 2");
        translation.add("Translation 3");
        translation.add("Translation 4");
        translation.add("Translation 5");

        FlashcardAdapter adapter = new FlashcardAdapter(this, flashcards);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FlashcardActivity.this, DisplayFlashcard.class);
                intent.putExtra("original", flashcards.get(position));
                intent.putExtra("translation", translation.get(position));
                startActivity(intent);
            }
        });


        makeFlashcardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        makeQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
