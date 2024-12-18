package com.wolfAndSheep.Service;

import com.wolfAndSheep.Model.Grid;

public class BacktrackingSolver {
    private final Grid grid;

    public int getStateSpaceCounter() {
        return stateSpaceCounter;
    }

    public void setStateSpaceCounter(int stateSpaceCounter) {
        this.stateSpaceCounter = stateSpaceCounter;
    }

    private int stateSpaceCounter;

    public BacktrackingSolver(Grid grid) {
        this.grid = grid;
    }

    public void solveScenario1() {
        System.out.println("Executing Backtracking for Scenario 1...");
        long startTime = System.nanoTime();
        stateSpaceCounter = 0;
        placeSheepBacktracking(0, 0);
        long endTime = System.nanoTime();

        System.out.println("Execution Time: " + (endTime - startTime) + " nanosec");
        System.out.println("State Spaces Explored: " + stateSpaceCounter);
        displayResult();
    }

    public void solveScenario2() {
        System.out.println("Executing Backtracking for Scenario 2...");
        long startTime = System.nanoTime();
        stateSpaceCounter = 0;
        placeWolvesBacktracking(0, 0);
        long endTime = System.nanoTime();

        System.out.println("Execution Time: " + (endTime - startTime) + "nanosec");
        System.out.println("State Spaces Explored: " + stateSpaceCounter);
        displayResult();
    }

    private boolean placeSheepBacktracking(int row, int col) {


        if (row == grid.getSize()) {
            return true;
        }

        int nextRow = (col == grid.getSize() - 1) ? row + 1 : row;
        int nextCol = (col == grid.getSize() - 1) ? 0 : col + 1;

        if (grid.isSafeForSheep(row, col)) {
            stateSpaceCounter++;
            grid.placeEntity(row, col, 's');
            if (placeSheepBacktracking(nextRow, nextCol)) return true;
            grid.placeEntity(row, col, '_');
        }

        return placeSheepBacktracking(nextRow, nextCol);
    }

    private boolean placeWolvesBacktracking(int row, int col) {


        if (row == grid.getSize()) {
            return true;
        }

        int nextRow = (col == grid.getSize() - 1) ? row + 1 : row;
        int nextCol = (col == grid.getSize() - 1) ? 0 : col + 1;

        if (grid.isSafeForWolf(row, col)) {
            stateSpaceCounter++;
            grid.placeEntity(row, col, 'w');
            if (placeWolvesBacktracking(nextRow, nextCol)) return true;
            grid.placeEntity(row, col, '_');
        }

        return placeWolvesBacktracking(nextRow, nextCol);
    }

    private void displayResult() {
        System.out.println("Final Board:");
        grid.printBoard();
        System.out.println("Wolves Positions: " + formatCoordinates(grid.getCoordinates('w')));
        System.out.println("Sheep Positions: " + formatCoordinates(grid.getCoordinates('s')));
    }

    private String formatCoordinates(java.util.List<int[]> coordinates) {
        StringBuilder sb = new StringBuilder();
        for (int[] coord : coordinates) {
            sb.append("(").append(coord[0]).append(", ").append(coord[1]).append(") ");
        }
        return sb.toString();
    }
}