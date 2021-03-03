package edu.temple.langexchange;

public class Account {

    int id;
    String name;
    String password;
    String pref_language;
    String learning_language;


    public Account(){

    }

    public Account(String name, String password){
        this.password = password;
    }

    public Account(int id, String name, String password, String pref_language, String learning_language) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.pref_language = pref_language;
        this.learning_language = learning_language;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPref_language() {
        return pref_language;
    }

    public void setPref_language(String pref_language) {
        this.pref_language = pref_language;
    }

    public String getLearning_language() {
        return learning_language;
    }

    public void setLearning_language(String learning_language) {
        this.learning_language = learning_language;
    }
}
