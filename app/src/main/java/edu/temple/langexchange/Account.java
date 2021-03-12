package edu.temple.langexchange;

public class Account {

    private int id;
    private String username;
    private String password;
    private String prefLang;
    private String learnLang;


    public Account(int id, String username, String password, String prefLang, String learnLang) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.prefLang = prefLang;
        this.learnLang = learnLang;
    }

    public Account(){

    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", prefLang='" + prefLang + '\'' +
                ", learnLang='" + learnLang + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrefLang() {
        return prefLang;
    }

    public void setPrefLang(String prefLang) {
        this.prefLang = prefLang;
    }

    public String getLearnLang() {
        return learnLang;
    }

    public void setLearnLang(String learnLang) {
        this.learnLang = learnLang;
    }
}
