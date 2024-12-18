package com.wolfAndSheep.Service;

import com.wolfAndSheep.Model.Grid;

import java.util.ArrayList;
import java.util.List;

public class OptimalBacktrackingSolver {
    private final Grid grid;
    private int maxSheepCount; // Scenario 1: Max sheep
    private int maxWolfCount;  // Scenario 2: Max wolves
    private List<int[]> bestWolves;
    private List<int[]> bestSheep;

    public int getStateCounter() {
        return stateCounter;
    }

    public void setStateCounter(int stateCounter) {
        this.stateCounter = stateCounter;
    }

    private int stateCounter; // State space counter

    public OptimalBacktrackingSolver(Grid grid) {
        this.grid = grid;
        this.maxSheepCount = 0;
        this.maxWolfCount = 0;
        this.bestWolves = new ArrayList<>();
        this.bestSheep = new ArrayList<>();
        this.stateCounter = 0;
    }

    // Scenario 1: Maximize sheep after placing wolves
    public void solveScenario1(int numWolves) {
        System.out.println("Calculating optimal solution for Scenario 1...");
        grid.resetGrid();
        stateCounter = 0;
        backtrackWolves(new ArrayList<>(), 0, 0, numWolves);
        System.out.println("Total States Explored: " + stateCounter);
        displayResult("Sheep", maxSheepCount);
    }

    // Scenario 2: Maximize wolves after placing sheep
    public void solveScenario2(int numSheep) {
        System.out.println("Calculating optimal solution for Scenario 2...");
        grid.resetGrid();
        stateCounter = 0;
        backtrackSheep(new ArrayList<>(), 0, 0, numSheep);
        System.out.println("Total States Explored: " + stateCounter);
        displayResult("Wolves", maxWolfCount);
    }

    private void backtrackWolves(List<int[]> currentWolves, int row, int col, int remainingWolves) {
        stateCounter++;

        if (remainingWolves == 0) {
            grid.resetGrid();
            for (int[] wolf : currentWolves) {
                grid.placeEntity(wolf[0], wolf[1], 'w');
            }
            List<int[]> sheepPositions = findSafeSheep();
            if (sheepPositions.size() > maxSheepCount) {
                maxSheepCount = sheepPositions.size();
                bestWolves = new ArrayList<>(currentWolves);
                bestSheep = new ArrayList<>(sheepPositions);
            }
            return;
        }

        if (row >= grid.getSize()) return;

        int nextRow = (col == grid.getSize() - 1) ? row + 1 : row;
        int nextCol = (col == grid.getSize() - 1) ? 0 : col + 1;

        if (grid.isSafeForWolf(row, col)) {
            currentWolves.add(new int[]{row, col});
            grid.placeEntity(row, col, 'w');
            backtrackWolves(currentWolves, nextRow, nextCol, remainingWolves - 1);
            grid.placeEntity(row, col, '_');
            currentWolves.remove(currentWolves.size() - 1);
        }

        backtrackWolves(currentWolves, nextRow, nextCol, remainingWolves);
    }

    private void backtrackSheep(List<int[]> currentSheep, int row, int col, int remainingSheep) {
        stateCounter++;

        if (remainingSheep == 0) {
            grid.resetGrid();
            for (int[] sheep : currentSheep) {
                grid.placeEntity(sheep[0], sheep[1], 's');
            }
            List<int[]> wolfPositions = findSafeWolves();
            if (wolfPositions.size() > maxWolfCount) {
                maxWolfCount = wolfPositions.size();
                bestSheep = new ArrayList<>(currentSheep);
                bestWolves = new ArrayList<>(wolfPositions);
            }
            return;
        }

        if (row >= grid.getSize()) return;

        int nextRow = (col == grid.getSize() - 1) ? row + 1 : row;
        int nextCol = (col == grid.getSize() - 1) ? 0 : col + 1;

        if (grid.isSafeForSheep(row, col)) {
            currentSheep.add(new int[]{row, col});
            grid.placeEntity(row, col, 's');
            backtrackSheep(currentSheep, nextRow, nextCol, remainingSheep - 1);
            grid.placeEntity(row, col, '_');
            currentSheep.remove(currentSheep.size() - 1);
        }

        backtrackSheep(currentSheep, nextRow, nextCol, remainingSheep);
    }

    private List<int[]> findSafeSheep() {
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.isSafeForSheep(i, j)) {
                    positions.add(new int[]{i, j});
                }
            }
        }
        return positions;
    }

    private List<int[]> findSafeWolves() {
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.isSafeForWolf(i, j)) {
                    positions.add(new int[]{i, j});
                }
            }
        }
        return positions;
    }

    private void displayResult(String entity, int maxCount) {
        System.out.println("Optimal Board:");
        grid.resetGrid();
        for (int[] wolf : bestWolves) {
            grid.placeEntity(wolf[0], wolf[1], 'w');
        }
        for (int[] sheep : bestSheep) {
            grid.placeEntity(sheep[0], sheep[1], 's');
        }
        grid.printBoard();
        System.out.println(entity + " Positions: " + formatCoordinates(entity.equals("Sheep") ? bestSheep : bestWolves));
        System.out.println("Maximum " + entity + " Placed: " + maxCount);
    }

    private String formatCoordinates(List<int[]> coordinates) {
        StringBuilder sb = new StringBuilder();
        for (int[] coord : coordinates) {
            sb.append("(").append(coord[0]).append(", ").append(coord[1]).append(") ");
        }
        return sb.toString();
    }
}