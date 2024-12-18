package com.wolfAndSheep.Model;

public class Board {
    private final int size;
    private final char[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        initializeBoard();
    }

    public char[][] getGrid() {
        return grid;
        
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                getGrid()[i][j] = '_';
            }
        }
    }

    public void placeWolves(int[][] wolfPositions) {
        for (int[] pos : wolfPositions) {
            getGrid()[pos[0]][pos[1]] = 'w';
        }
    }

    public void placeSheep(int[][] sheepPositions) {
        for (int[] pos : sheepPositions) {
            getGrid()[pos[0]][pos[1]] = 's';
        }
    }

    public void calculateSafeSheepPositions() {
        Solver solver = new Solver(size, getGrid());
        solver.placeSafeSheep();
    }

    public void calculateSafeWolfPositions() {
        Solver solver = new Solver(size, getGrid());
        solver.placeSafeWolves();
    }

    public void displayBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(getGrid()[i][j] + " ");
            }
            System.out.println();
        }
    }
}
