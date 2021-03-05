package edu.temple.langexchange;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FlashcardActivity extends AppCompatActivity {

    TextView text;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        getSupportActionBar().setTitle(R.string.flashcard_title);

        text = findViewById(R.id.textView);
        list = findViewById(R.id.flashcardList);

        text.setText(R.string.flashcard_instructions);

        ArrayList flashcards = new ArrayList<String>();
        flashcards.add("Example 1");
        flashcards.add("Example 2");
        flashcards.add("Example 3");
        flashcards.add("Example 4");
        flashcards.add("Example 5");

        FlashcardAdapter adapter = new FlashcardAdapter(this, flashcards);

        list.setAdapter(adapter);
    }
}
