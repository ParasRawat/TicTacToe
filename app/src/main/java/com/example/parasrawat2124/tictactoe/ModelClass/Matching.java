package com.example.parasrawat2124.tictactoe.ModelClass;

public class Matching {
    String player1;
    String player2;
    String player1move;
    String player2move;
    String result;
    String player1status;
    String player2status;

    public Matching() {
    }

    public Matching(String player1, String player2, String player1move, String player2move, String result,String player1status,String player2status) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1move = player1move;
        this.player2move = player2move;
        this.result = result;
        this.player1status=player1status;
        this.player2status=player2status;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getPlayer1move() {
        return player1move;
    }

    public String getPlayer1status() {
        return player1status;
    }

    public String getPlayer2status() {
        return player2status;
    }

    public String getPlayer2move() {
        return player2move;
    }

    public String getResult() {
        return result;
    }
}
