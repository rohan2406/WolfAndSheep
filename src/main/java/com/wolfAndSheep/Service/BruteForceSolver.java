package com.wolfAndSheep.Service;

import com.wolfAndSheep.Model.Grid;

public class BruteForceSolver {
    private final Grid grid;

    public int getStateSpaceCounter() {
        return stateSpaceCounter;
    }

    public void setStateSpaceCounter(int stateSpaceCounter) {
        this.stateSpaceCounter = stateSpaceCounter;
    }

    private int stateSpaceCounter;

    public BruteForceSolver(Grid grid) {
        this.grid = grid;
    }

    public void solveScenario1() {
        System.out.println("Executing Brute Force for Scenario 1...");
        long startTime = System.nanoTime();
        stateSpaceCounter = 0; // Reset the counter
        placeSheepBruteForce();
        long endTime = System.nanoTime();

        System.out.println("State Spaces Explored: " + stateSpaceCounter);

        System.out.println("Execution Time: " + (endTime - startTime) + "nanosec");
        displayResult();
    }

    public void solveScenario2() {
        System.out.println("Executing Brute Force for Scenario 2...");
        long startTime = System.nanoTime();
        stateSpaceCounter = 0; // Reset the counter
        placeWolvesBruteForce();
        long endTime = System.nanoTime();
        System.out.println("State Spaces Explored: " + stateSpaceCounter);

        System.out.println("Execution Time: " + (endTime - startTime) + " nanosec");
        displayResult();
    }

    private void placeSheepBruteForce() {

        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                stateSpaceCounter++;
                if (grid.isSafeForSheep(i, j)) {
                    grid.placeEntity(i, j, 's');
                }
                if(grid.getEntityAt(i, j)=='w')
                    stateSpaceCounter--;

            }
        }
    }


    private void placeWolvesBruteForce() {

        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                stateSpaceCounter++;
                if (grid.isSafeForWolf(i, j)) {

                    grid.placeEntity(i, j, 'w');
                }
                if(grid.getEntityAt(i, j)=='s')
                    stateSpaceCounter--;
            }
        }
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