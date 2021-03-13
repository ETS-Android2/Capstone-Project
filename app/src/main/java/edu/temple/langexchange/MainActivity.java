package edu.temple.langexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Needed in Activities that access that database
    DatabaseReference ref;
    List<Flashcards> flashcardList = new ArrayList<>();

    Account account;
    long maxid;
    TextView Username, Password, PrefLang, LearnLang, inputFlashcard, showFlashcard;
    Button createAccount, makeFlashcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        PrefLang = findViewById(R.id.PrefLang);
        LearnLang = findViewById(R.id.LearnLang);
        inputFlashcard = findViewById(R.id.inputFlashcard);
        showFlashcard = findViewById(R.id.showFlashcard);
        makeFlashcard = findViewById(R.id.makeFlashcard);
        createAccount = findViewById(R.id.CreateAccount);
        account = new Account();

        //Initialize the firebase database
        //This is needed in the activates that access it
        ref = FirebaseDatabase.getInstance().getReference().child("Account");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account;


                String username = Username.getText().toString();
                String password = Password.getText().toString();
                String prefLang = PrefLang.getText().toString();
                String learnLang = LearnLang.getText().toString();

                account = new Account(0, username, password, prefLang, learnLang);

                //This adds the object account to the database

                ref.child(String.valueOf(maxid+1)).setValue(account);
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();



            }
        });

        makeFlashcard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                ref = FirebaseDatabase.getInstance().getReference().child("Account").child("1");

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String username = snapshot.child("username").getValue().toString();
                        String password = snapshot.child("password").getValue().toString();

                            //makeFlashcard.setText(username);


                        Flashcards flashcard = new Flashcards(0, username, username, username);
                        Flashcards flashcard2 =  new Flashcards(1, password, password, password);
                        flashcardList.add(flashcard);
                        flashcardList.add(flashcard2);


                        Toast.makeText(MainActivity.this, flashcardList.toString(), Toast.LENGTH_SHORT).show();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });







    }
}