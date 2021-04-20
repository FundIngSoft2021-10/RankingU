package com.example.rankingu.Classes;

public class Error {

    String user, error;

    public Error() {
    }

    public Error(String user, String error) {
        this.user = user;
        this.error = error;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
