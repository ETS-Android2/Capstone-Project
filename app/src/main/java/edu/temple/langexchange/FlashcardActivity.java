package edu.temple.langexchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FlashcardActivity extends AppCompatActivity {

    private static int counter = 0;

    TextView text;
    GridView gridView;

    Button makeFlashcardBtn;
    Button makeQuizBtn;

    ArrayList<Flashcards> flashcardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        getSupportActionBar().setTitle(R.string.flashcard_title);

        text = findViewById(R.id.flashcardText);
        gridView = findViewById(R.id.flashcardGrid);

        makeFlashcardBtn = findViewById(R.id.createFlashcardBtn);
        makeQuizBtn = findViewById(R.id.createQuizBtn);

        text.setText(R.string.flashcard_instructions);

        flashcardList.add(new Flashcards(1, "Hello", "Hola", "Expression with which you greet"));
        flashcardList.add(new Flashcards(2, "Hello", "Bonjour", "Expression with which you greet"));

        for (Flashcards card : flashcardList) {
            counter++;
        }

        FlashcardAdapter adapter = new FlashcardAdapter(this, flashcardList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FlashcardActivity.this, DisplayFlashcard.class);
                intent.putExtra("original", flashcardList.get(position).originalWord);
                intent.putExtra("translation", flashcardList.get(position).translatedWord);
                startActivity(intent);
            }
        });


        makeFlashcardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardActivity.this, MakeFlashcard.class);
                startActivityForResult(intent, 1);
            }
        });

        makeQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardActivity.this, QuizActivity.class);
                intent.putExtra("flashcardArr", flashcardList);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String original = data.getStringExtra("originalWord");
            String translation = data.getStringExtra("translatedWord");
            String definition = data.getStringExtra("definition");

            // add the new flashcard onto the list
            flashcardList.add(new Flashcards(counter += 1, translation, original, definition));

            // reference layout from xml
            gridView = findViewById(R.id.flashcardGrid);

            // refresh the grid view
            FlashcardAdapter adapter = new FlashcardAdapter(this, flashcardList);
            gridView.setAdapter(adapter);
        }
    }
}
