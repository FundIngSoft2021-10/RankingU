package com.example.rankingu.Classes;

public class Suggstion {

    String user, sug;

    public Suggstion() {
    }

    public Suggstion(String user, String sug) {
        this.user = user;
        this.sug = sug;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSug() {
        return sug;
    }

    public void setSug(String sug) {
        this.sug = sug;
    }
}
