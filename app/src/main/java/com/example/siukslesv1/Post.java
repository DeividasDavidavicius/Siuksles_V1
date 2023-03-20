package com.example.siukslesv1;

public class Post {
    private String email;
    private String name;
    private String location;
    private String uri;
    private int type;
    private int voteCount;

    public Post() {
    }

    public Post(String email, String name, String location, String URI, int type) {
        this.email = email;
        this.name = name;
        this.location = location;
        this.uri = uri;
        this.type = type;
        this.voteCount = 0;
    }

    public String getEmail() {
        return email;
    }
    public String getName() { return name; }
    public String getURI() {return uri; }
    public String getLocation() { return location; }
    public int getType() { return type; }
    public int getVoteCount() { return voteCount; }

    public void add()
    {
        voteCount = voteCount+1;
    }
}