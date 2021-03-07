package edu.temple.langexchange;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FlashcardDatabase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "flashcardManager";
    private static final String TABLE_CONTENTS = "flashcards";

    private static final String KEY_ID = "id";
    private static final String ORIGINAL_WORD = "originalWord";
    private static final String TRANSLATED_WORD = "translatedWord";



    public FlashcardDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE flashcardManager" + TABLE_CONTENTS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY," + ORIGINAL_WORD + " TEXT,"
                + TRANSLATED_WORD + " TEXT" + ")";

        db.execSQL(CREATE_ACCOUNTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENTS);

        onCreate(db);
    }

    void addFlashcard(Flashcards flashcard){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, flashcard.getId());
        values.put(ORIGINAL_WORD, flashcard.getOriginalWord());
        values.put(TRANSLATED_WORD, flashcard.getTranslatedWord());

        db.insert(TABLE_CONTENTS, null, values);
        db.close();
    }

    Flashcards getFlashcard(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTENTS, new String[]{KEY_ID, ORIGINAL_WORD, TRANSLATED_WORD}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();

        }

        Flashcards flashcards = new Flashcards(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return flashcards;
    }

    public List<Flashcards> getAllFlashcards(){
        List<Flashcards> flashcardsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM flashcardManager" + TABLE_CONTENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Flashcards flashcard = new Flashcards();
                flashcard.setId(Integer.parseInt(cursor.getString(0)));
                flashcard.setOriginalWord(cursor.getString(1));
                flashcard.setTranslatedWord(cursor.getString(2));


                flashcardsList.add(flashcard);
            } while (cursor.moveToNext());
        }

        return flashcardsList;
    }

    public int updateFlashcard(Flashcards flashcard) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, flashcard.getId());
        values.put(ORIGINAL_WORD, flashcard.getOriginalWord());
        values.put(TRANSLATED_WORD, flashcard.getTranslatedWord());

        return db.update(TABLE_CONTENTS, values, KEY_ID + "=?",
                new String[]{String.valueOf(flashcard.getId())});

    }

    public void deleteFlashcard(Flashcards flashcard){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTENTS, KEY_ID + "=?",
                new String[]{String.valueOf(flashcard.getId())});
        db.close();
    }

    public int getFlashcardCount(){
        String countQuery = "SELECT * FROM flashcardManager" + TABLE_CONTENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
