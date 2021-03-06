package com.example.parasrawat2124.tictactoe.ModelClass;

import java.util.ArrayList;

public class UserProfile {

    String username,email,status,uri;
    ArrayList<String> friends=new ArrayList<>(),matches=new ArrayList<>(),reqreceived=new ArrayList<>(),reqsent=new ArrayList<>();
    int won,lost,gravities,bulls,rank,score,bullspent,gravityspent;

    public UserProfile(){}

    public UserProfile(String name,String email,String uri){
        username=name;
        this.email=email;
        status="offline";
        this.uri=uri;
//        friends.add("");
//        matches.add("");
//        reqreceived.add("");
//        reqsent.add("");
        won=0;
        lost=0;
        gravities=0;
        bulls=0;
        rank=0;
        score=0;
        bullspent=0;
        gravityspent=0;
    }

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

    public int getGravities() {
        return gravities;
    }

    public void setGravities(int gravities) {
        this.gravities = gravities;
    }

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getBullspent() {
        return bullspent;
    }

    public void setBullspent(int bullspent) {
        this.bullspent = bullspent;
    }

    public int getGravityspent() {
        return gravityspent;
    }

    public void setGravityspent(int gravityspent) {
        this.gravityspent = gravityspent;
    }
}
