package edu.temple.langexchange;

public class Flashcards {

    int id;
    String translatedWord;
    String originalWord;


    public Flashcards(){

    }

    public Flashcards(int id, String translatedWord, String originalWord){
        this.id = id;
        this.translatedWord = translatedWord;
        this.originalWord = originalWord;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }





}
