package edu.temple.langexchange;

import android.content.ContentValues;
import android.content.Context;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class AccountDatabase {

    DatabaseReference ref;

    long maxid;


    public boolean addUser(Account account) {
        final boolean[] success = new boolean[1];
        ref = FirebaseDatabase.getInstance().getReference().child("Account");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //This adds the object account to the database
                ref.child(String.valueOf(maxid + 1)).setValue(account);
                success[0] = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                success[0] = false;
            }
        });
        return success[0];
    }
}