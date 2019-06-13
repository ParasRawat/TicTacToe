package com.example.parasrawat2124.tictactoe.ModelClass;

import java.util.ArrayList;

public class DummyMatchModel {
    ArrayList<ArrayList<Integer>> grid;
    String turn;

    public DummyMatchModel() {
    }

    public DummyMatchModel(ArrayList<ArrayList<Integer>> grid, String turn) {
        this.grid = grid;
        this.turn = turn;
    }

    public ArrayList<ArrayList<Integer>> getGrid() {
        return grid;
    }

    public String getTurn() {
        return turn;
    }
}
