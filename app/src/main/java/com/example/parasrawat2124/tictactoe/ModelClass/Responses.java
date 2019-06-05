package com.example.parasrawat2124.tictactoe.ModelClass;

public class Responses {
    String block11;
    String block12;
    String block13;
    String block21;
    String block22;
    String block23;
    String block31;
    String block32;
    String block33;
    String player1turn;
    String player2turn;
    String winner;
    String turn;
    String player1,player2;

    public Responses() {
    }

    public Responses(String block11, String block12, String block13, String block21, String block22, String block23, String block31, String block32, String block33, String player1turn, String player2turn, String winner,String turn
                        ,String player1,String player2) {
        this.block11 = block11;
        this.block12 = block12;
        this.block13 = block13;
        this.block21 = block21;
        this.block22 = block22;
        this.block23 = block23;
        this.block31 = block31;
        this.block32 = block32;
        this.block33 = block33;
        this.player1turn = player1turn;
        this.player2turn = player2turn;
        this.winner = winner;
        this.turn=turn;
        this.player1=player1;
        this.player2=player2;
    }

    public String getBlock11() {
        return block11;
    }

    public String getBlock12() {
        return block12;
    }

    public String getBlock13() {
        return block13;
    }

    public String getBlock21() {
        return block21;
    }

    public String getBlock22() {
        return block22;
    }

    public String getBlock23() {
        return block23;
    }

    public String getBlock31() {
        return block31;
    }

    public String getBlock32() {
        return block32;
    }

    public String getBlock33() {
        return block33;
    }

    public String getPlayer1turn() {
        return player1turn;
    }

    public String getPlayer2turn() {
        return player2turn;
    }

    public String getWinner() {
        return winner;
    }

    public String getTurn() {
        return turn;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }
}
