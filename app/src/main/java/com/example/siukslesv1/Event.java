package com.example.siukslesv1;

import java.util.Date;
import java.util.UUID;

public class Event {
    private String email;
    private String name;
    private String location;
    private String uri;
    private int type;
    private String eventid;
    private long creationTime;


    public Event() {
    }

    public Event(String email, String name, String location, String uri, int type, long creationTime, String eventid) {
        this.email = email;
        this.name = name;
        this.location = location;
        this.uri = uri;
        this.type = type;
        this.creationTime = creationTime;
        this.eventid = eventid
        ;
    }

    public String getEmail() {
        return email;
    }
    public String getName() { return name; }
    public String getUri() {return uri; };
    public String getLocation() { return location; }
    public int getType() { return type; }
    public String getEventid() { return eventid; }
    public long getTime(){return creationTime;}
}

