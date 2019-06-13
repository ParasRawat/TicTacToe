package com.example.parasrawat2124.tictactoe.ModelClass;

import java.util.ArrayList;

public class UserProfile {

    String username,email,status;
    ArrayList<String> friends=new ArrayList<>(),matches=new ArrayList<>(),reqreceived=new ArrayList<>(),reqsent=new ArrayList<>();
    int won,lost;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public ArrayList<String> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<String> matches) {
        this.matches = matches;
    }

    public ArrayList<String> getReqreceived() {
        return reqreceived;
    }

    public void setReqreceived(ArrayList<String> reqreceived) {
        this.reqreceived = reqreceived;
    }

    public ArrayList<String> getReqsent() {
        return reqsent;
    }

    public void setReqsent(ArrayList<String> reqsent) {
        this.reqsent = reqsent;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }
}
