package edu.temple.langexchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MakeFlashcard extends AppCompatActivity {

    EditText originalWordInput;
    EditText translatedWordInput;
    EditText definitionInput;

    Button addFlashcardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_flashcard);

        originalWordInput = findViewById(R.id.originalWordInput);
        translatedWordInput = findViewById(R.id.translatedWordInput);
        definitionInput = findViewById(R.id.definitionInput);

        addFlashcardBtn = findViewById(R.id.addFlashcardBtn);

        addFlashcardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // convert input fields to String object
                String originalWord = originalWordInput.getText().toString();
                String translatedWord = translatedWordInput.getText().toString();
                String definition = definitionInput.getText().toString();

                // check that input fields are not empty
                if (originalWord.length() != 0 && translatedWord.length() != 0 && definition.length() != 0) {
                    Intent intent = new Intent();

                    // this declares data to return to the previous activity
                    intent.putExtra("originalWord", originalWord);
                    intent.putExtra("translatedWord", translatedWord);
                    intent.putExtra("definition", definition);

                    // return a code for previous activity to handle
                    setResult(RESULT_OK, intent);

                    // finish activity
                    finish();
                }
            }
        });
    }
}
