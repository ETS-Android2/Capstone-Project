package edu.temple.langexchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChatRoomChoice extends AppCompatActivity {

//    Button btnSpa, btnGer, btnEng, btnFre, goToFlashcards;
    Spinner spin;
    Button submitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_choice);

//        btnSpa = findViewById(R.id.SpanishRoomBtn);
//        btnEng = findViewById(R.id.EnglishRoomBtn);
//        btnGer = findViewById(R.id.GermanRoomBtn);
//        btnFre = findViewById(R.id.FrenchRoomBtn);
//        goToFlashcards = findViewById(R.id.go_to_flashcards_btn);

        spin = findViewById(R.id.spinner);
        submitBtn = findViewById(R.id.selectLangBtn);

        Intent prevIntent = getIntent();
        String userName = prevIntent.getStringExtra("username");
//        int userId = prevIntent.getIntExtra("userId", 0);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages_array, android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = spin.getSelectedItem().toString();
                Intent intent = new Intent(ChatRoomChoice.this, ChatSystem.class);
                intent.putExtra("username", userName);
                intent.putExtra("langSelected", selected);
                intent.putExtra("channelID","K37YpRtGTMBC9JAZ");
                startActivity(intent);
            }
        });
//        btnSpa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ChatRoomChoice.this, ChatSystem.class);
//                intent.putExtra("channelID", "K37YpRtGTMBC9JAZ");
//                intent.putExtra("langSelected", "SPANISH");
//                intent.putExtra("username", userName);
//
//                startActivity(intent);
//            }
//        });
//
//        btnGer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ChatRoomChoice.this, ChatSystem.class);
//                intent.putExtra("channelID", "iTzl5dVNhZweOFTo");
//                intent.putExtra("langSelected", "GERMAN");
//                intent.putExtra("username", userName);
//
//                startActivity(intent);
//            }
//        });
//
//        btnEng.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ChatRoomChoice.this, ChatSystem.class);
//                intent.putExtra("channelID", "9Re6IIi9ZhoqxGbc");
//                intent.putExtra("langSelected", "ENGLISH");
//                intent.putExtra("username", userName);
//
//                startActivity(intent);
//            }
//        });
//
//        btnFre.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ChatRoomChoice.this, ChatSystem.class);
//                intent.putExtra("channelID", "Pbf9jcw2NrgUxB2B");
//                intent.putExtra("langSelected", "FRENCH");
//                intent.putExtra("username", userName);
//
//                startActivity(intent);
//            }
//        });
//
//        goToFlashcards.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ChatRoomChoice.this, FlashcardActivity.class);
//                intent.putExtra("username", userName);
//                intent.putExtra("userId", userId);
//                startActivity(intent);
//            }
//        });
    }
}
