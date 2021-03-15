package edu.temple.langexchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.temple.langexchange.ui.login.LoginViewModel;
import edu.temple.langexchange.ui.login.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {

    private TextView mTextView;
    private int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);

        mTextView = (TextView) findViewById(R.id.text);
            final EditText usernameEditText = findViewById(R.id.username);
            final EditText passwordEditText = findViewById(R.id.password);
            final Button loginButton = findViewById(R.id.login);
            Button registerButton = (Button) findViewById(R.id.register);

            registerButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    startActivity(new Intent(LoginActivity.this, Register.class));
                }
            });

            loginButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    AccountDatabase db = new AccountDatabase();
                    db.findUser(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    int result = db.currentUserId;
                    if(result == -1){
                        Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        result = loggedInUserId;
                        startActivity(new Intent(LoginActivity.this, FlashcardActivity.class));
                    }
                }
            });

        // Enables Always-on

    }
}