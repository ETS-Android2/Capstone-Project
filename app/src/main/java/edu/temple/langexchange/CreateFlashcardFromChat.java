package edu.temple.langexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateFlashcardFromChat extends AppCompatActivity {


    ListView listView;
    String word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard_from_chat);


        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        String phrase = intent.getExtras().getString("phrase");
        ArrayList<String> arrayList = new ArrayList<>();
        String[] result = phrase.split("\\s+");
        String firstWord = result[0];
       //arrayList.add(result[0]);
       // result = phrase.split("\\s+");
       for(int count = 0; count<result.length; count++){
           arrayList.add(result[count]);

        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word = (String) listView.getItemAtPosition(position);

            }
        });
    }



}