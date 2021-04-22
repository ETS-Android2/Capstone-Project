package edu.temple.langexchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;

public class ChatRoomChoice extends AppCompatActivity {

    //    Button btnSpa, btnGer, btnEng, btnFre, goToFlashcards;
    Spinner spin;
    Button submitBtn;
    ListView listView;
    ArrayList<String> availableRooms = new ArrayList<>();

    DatabaseReference ref;

    ArrayAdapter sAdapter;
    ArrayAdapter lAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_choice);

        System.out.println("New instance of ChatRoomChoice");

        setupBottomNavigationView();
        spin = findViewById(R.id.spinner);
        submitBtn = findViewById(R.id.selectLangBtn);
        listView = findViewById(R.id.availableRoom);

        sAdapter = new ArrayAdapter(ChatRoomChoice.this, android.R.layout.simple_spinner_item, Translator.getLanguages());
        spin.setAdapter(sAdapter);

        lAdapter = new ArrayAdapter(ChatRoomChoice.this, android.R.layout.simple_list_item_1, availableRooms);
        listView.setAdapter(lAdapter);

        ref = FirebaseDatabase.getInstance().getReference().child("ChatRoom");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                availableRooms.clear();
                if(snapshot.hasChildren())
                {
                    for (DataSnapshot childSnap : snapshot.getChildren())
                    {
                        availableRooms.add(childSnap.child("langChosen").getValue().toString());
                    }
                }
                if(!availableRooms.isEmpty())
                {
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ChatRoomChoice.this, ChatSystem.class);
                            intent.putExtra("langSelected", availableRooms.get(position));
                            System.out.println("Available Room passed: " + availableRooms.get(position));
                            startActivity(intent);
                        }
                    });
                }
                lAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedLang = spin.getSelectedItem().toString();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> usedLang = new ArrayList<>();
                        for(DataSnapshot childSnap : snapshot.getChildren())
                        {
                            if(snapshot.getChildren() != null)
                            {
                                usedLang.add(childSnap.child("langChosen").getValue().toString());
                                System.out.println("used lang:" + usedLang);
                            }
                        }

                        if (!usedLang.contains(selectedLang)) {
                            Intent intent = new Intent(ChatRoomChoice.this, ChatSystem.class);
                            intent.putExtra("langSelected", selectedLang);
                            startActivity(intent);
                        } else if(usedLang.contains(selectedLang)){
                            Toast.makeText(ChatRoomChoice.this, "Room already exist", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navBar);
        BottomNavigationHelper.enableNavigation(this, bottomNavigationViewEx);

    }
}
