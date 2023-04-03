package com.example.siukslesv1;

import java.util.UUID;

public class User {
    private String email;
    private String password;
    private String username;
    private String title;
    private int points;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String username, String title, int points) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.title = title;
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public int getPoints() {
        return points;
    }
}
