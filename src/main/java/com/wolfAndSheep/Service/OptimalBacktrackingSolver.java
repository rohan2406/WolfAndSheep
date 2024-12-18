package com.wolfAndSheep.Service;

import com.wolfAndSheep.Model.Grid;

import java.util.List;

public class OptimalBacktrackingSolver {
    private final Grid grid;
    private int maxCount = 0;

    public int getStateSpaceCounter() {
        return stateSpaceCounter;
    }

    public void setStateSpaceCounter(int stateSpaceCounter) {
        this.stateSpaceCounter = stateSpaceCounter;
    }

    private int stateSpaceCounter;

    public OptimalBacktrackingSolver(Grid grid) {
        this.grid = grid;
    }

    public void solveScenario1(List<int[]> wolfPositions) {
        System.out.println("Calculating optimal solution for Scenario 1 using Backtracking...");
        long startTime = System.nanoTime();
        stateSpaceCounter = 0;

        grid.resetGrid();
        for (int[] pos : wolfPositions) {
            grid.placeEntity(pos[0], pos[1], 'w');
        }
        backtrackSheepPlacement(0, 0, 0);

        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) + "nanosec");
        System.out.println("State Spaces Explored: " + stateSpaceCounter);
        displayResult();
    }

    public void solveScenario2(List<int[]> sheepPositions) {
        System.out.println("Calculating optimal solution for Scenario 2 using Backtracking...");
        long startTime = System.nanoTime();
        stateSpaceCounter = 0;

        grid.resetGrid();
        for (int[] pos : sheepPositions) {
            grid.placeEntity(pos[0], pos[1], 's');
        }
        backtrackWolfPlacement(0, 0, 0);

        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("State Spaces Explored: " + stateSpaceCounter);
        displayResult();
    }

    private void backtrackSheepPlacement(int row, int col, int count) {
        stateSpaceCounter++;
        if (row == grid.getSize()) {
            maxCount = Math.max(maxCount, count);
            return;
        }

        int nextRow = (col == grid.getSize() - 1) ? row + 1 : row;
        int nextCol = (col == grid.getSize() - 1) ? 0 : col + 1;

        if (grid.isSafeForSheep(row, col)) {
            grid.placeEntity(row, col, 's');
            backtrackSheepPlacement(nextRow, nextCol, count + 1);
        }

        backtrackSheepPlacement(nextRow, nextCol, count);
    }

    private void backtrackWolfPlacement(int row, int col, int count) {
        stateSpaceCounter++;

        if (row == grid.getSize()) {
            maxCount = Math.max(maxCount, count);
            return;
        }

        int nextRow = (col == grid.getSize() - 1) ? row + 1 : row;
        int nextCol = (col == grid.getSize() - 1) ? 0 : col + 1;

        if (grid.isSafeForWolf(row, col)) {
            grid.placeEntity(row, col, 'w');
            backtrackWolfPlacement(nextRow, nextCol, count + 1);
            grid.placeEntity(row, col, '_');
        }

        backtrackWolfPlacement(nextRow, nextCol, count);
    }

    private void displayResult() {
        System.out.println("Optimal Board:");
        grid.printBoard();
        System.out.println("Wolves Positions: " + formatCoordinates(grid.getCoordinates('w')));
        System.out.println("Sheep Positions: " + formatCoordinates(grid.getCoordinates('s')));
    }

    private String formatCoordinates(List<int[]> coordinates) {
        StringBuilder sb = new StringBuilder();
        for (int[] coord : coordinates) {
            sb.append("(").append(coord[0]).append(", ").append(coord[1]).append(") ");
        }
        return sb.toString();
    }
}