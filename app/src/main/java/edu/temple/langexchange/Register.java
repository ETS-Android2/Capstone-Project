package edu.temple.langexchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.temple.langexchange.ui.login.LoginActivity;

public class Register extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextView = (TextView) findViewById(R.id.text);
        final EditText usernameEditText = findViewById(R.id.editTextEmail);
        final EditText passwordEditText = findViewById(R.id.editTextPassword);
        final EditText targetEditText = findViewById(R.id.editTextTargetLang);
        final EditText nativeEditText = findViewById(R.id.editTextNativeLang);


        Button submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                    Account account;
                    try {
                        account = new Account(0, usernameEditText.getText().toString(), passwordEditText.getText().toString(), nativeEditText.getText().toString(), targetEditText.getText().toString());

                        Toast.makeText(Register.this, account.toString(), Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e){
                        Toast.makeText(Register.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                        account = new Account(1, "error","error","error","error");
                    }

                    AccountDatabase accountDatabase = new AccountDatabase();

                    boolean success = accountDatabase.addUser(account);

                    if(success == true){
                        Toast.makeText(Register.this, "Sucess", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(Register.this, "Register Failed", Toast.LENGTH_SHORT).show();

                    }
                    startActivity(new Intent(Register.this, FlashcardActivity.class));


                }
            });


            }
}

        // Enables Always-on
        //setAmbientEnabled();

