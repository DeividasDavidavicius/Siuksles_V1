package com.example.siukslesv1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Event {
    private String email;
    private String name;
    private String location;
    private String uri;
    private int type;
    private String eventid;
    private long creationTime;
    private List<String> participants;
    private long eventstart;
    private long eventend;
    public Event() {
    }

    public Event(String email, String name, String location, String uri, int type, long creationTime, String eventid, List<String> participants, long eventstart, long eventend) {
        this.email = email;
        this.name = name;
        this.location = location;
        this.uri = uri;
        this.type = type;
        this.creationTime = creationTime;
        this.eventid = eventid;
        this.participants = participants;
        this.eventstart = eventstart;
        this.eventend = eventend;
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
    public long getStart() {return eventstart; }
    public long getEnd() {return eventend;}

    public void addParticipant(String userId)
    {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        participants.add(userId);
    }
    public List<String> getParticipants(){
        return participants;
    }
    public void setParticipants(List<String> participants){
        this.participants = participants;
    }
}

