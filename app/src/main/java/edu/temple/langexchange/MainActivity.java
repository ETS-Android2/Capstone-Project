package edu.temple.langexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    DatabaseReference ref;
    Account account;
    long maxid;
    TextView Username, Password, PrefLang, LearnLang;
    Button createAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        PrefLang = findViewById(R.id.PrefLang);
        LearnLang = findViewById(R.id.LearnLang);
        createAccount = findViewById(R.id.CreateAccount);
        account = new Account();

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

                account.setUsername(username);
                account.setPassword(password);
                account.setPrefLang(prefLang);
                account.setLearnLang(learnLang);

                ref.child(String.valueOf(maxid+1)).setValue(account);
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();



            }
        });





    }
}