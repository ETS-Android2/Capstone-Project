package edu.temple.langexchange;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "accountManager";
    private static final String TABLE_CONTENTS = "accounts";
    private static final String KEY_ID = "id";
    //foreign key that will match to other database to see what cards refer to which account
    //unique ID

    private static final String KEY_NAME = "username";
    private static final String PASSWORD = "password";
    private static final String PREF_LANGUAGE = "preferedLanguage";
    private static final String LEARNING_LANGUAGE = "learningLanguage";



    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE accountManager" + TABLE_CONTENTS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + PASSWORD + " TEXT" + PREF_LANGUAGE + "TEXT,"
                + LEARNING_LANGUAGE + " TEXT" + ")";

        db.execSQL(CREATE_ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENTS);

     onCreate(db);
    }

    boolean addUser(Account account){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, account.getName());
        values.put(PASSWORD, account.getPassword());
        values.put(PREF_LANGUAGE, account.getPref_language());
        values.put(LEARNING_LANGUAGE, account.getLearning_language());

        long insert = db.insert(TABLE_CONTENTS, null, values);
        db.close();
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }

    }

    Account getAccount(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTENTS, new String[]{KEY_ID, KEY_NAME, PASSWORD}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();

        }

        Account account = new Account(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return account;
    }

    public List<Account> getAllAccounts(){
        List<Account> accountList = new ArrayList<>();

        String selectQuery = "SELECT * FROM accountManager" + TABLE_CONTENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Account account = new Account();
                account.setId(Integer.parseInt(cursor.getString(0)));
                account.setName(cursor.getString(1));
                account.setPassword(cursor.getString(2));
                account.setPref_language(cursor.getString(3));
                account.setLearning_language(cursor.getString(4));


                accountList.add(account);
            } while (cursor.moveToNext());
        }

        return accountList;
    }

    public int updateAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, account.getName());
        values.put(PASSWORD, account.getPassword());
        values.put(PREF_LANGUAGE, account.getPref_language());
        values.put(LEARNING_LANGUAGE, account.getLearning_language());

        return db.update(TABLE_CONTENTS, values, KEY_ID + "=?",
                new String[]{String.valueOf(account.getId())});

    }

    public void deleteAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTENTS, KEY_ID + "=?",
                new String[]{String.valueOf(account.getId())});
        db.close();
    }

    public int getAccountCount(){
        String countQuery = "SELECT * FROM accountManager" + TABLE_CONTENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
