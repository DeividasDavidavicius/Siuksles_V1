package com.example.siukslesv1;

public class Post {
    private String email;
    private String name;
    private String location;
    private String URI;
    private int type;
    private int voteCount;

    public Post() {
    }

    public Post(String email, String name, String location, String URI, int type) {
        this.email = email;
        this.name = name;
        this.location = location;
        this.URI = URI;
        this.type = type;
        this.voteCount = 0;
    }

    public String getEmail() {
        return email;
    }
    public String getName() { return name; }
    public String getURI() {return URI; };
    public String getLocation() { return location; }
    public int getType() { return type; }
    public int getVoteCount() { return voteCount; }
}

