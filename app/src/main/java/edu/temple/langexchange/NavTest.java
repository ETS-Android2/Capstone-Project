package edu.temple.langexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class NavTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_test);

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navBar);
        BottomNavigationHelper.enableNavigation(NavTest.this, bottomNavigationViewEx);

    }
}