package com.wolfAndSheep.Service;

import com.wolfAndSheep.Model.Grid;

import java.util.ArrayList;
import java.util.List;

public class OptimalBruteForceSolver {
    private final Grid grid;

    public int getStateSpaceCounter() {
        return stateSpaceCounter;
    }

    public void setStateSpaceCounter(int stateSpaceCounter) {
        this.stateSpaceCounter = stateSpaceCounter;
    }

    private int stateSpaceCounter;

    public OptimalBruteForceSolver(Grid grid) {
        this.grid = grid;
    }

    public void solveScenario1(List<int[]> wolfPositions) {
        System.out.println("Calculating optimal solution for Scenario 1 using Brute Force...");
        long startTime = System.nanoTime();
        stateSpaceCounter = 0;


        int maxSheep = 0;
        List<int[]> bestWolves = new ArrayList<>(wolfPositions);
        grid.resetGrid();

        // Try all wolf placements
        for (List<int[]> placement : generateCombinations(grid.getSize(), wolfPositions.size())) {
            grid.resetGrid();
            for (int[] pos : placement) {
                grid.placeEntity(pos[0], pos[1], 'w');
            }
            int sheepCount = countSafeSheep();
            if (sheepCount > maxSheep) {
                maxSheep = sheepCount;
                bestWolves = new ArrayList<>(placement);
            }
        }

        // Place the best configuration of wolves and calculate remaining sheep
        grid.resetGrid();
        for (int[] pos : bestWolves) {
            grid.placeEntity(pos[0], pos[1], 'w');
        }
        placeAllSafeSheep();

        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) + " nanosec");
        System.out.println("State Spaces Explored: " + stateSpaceCounter);
        displayResult();
    }

    public void solveScenario2(List<int[]> sheepPositions) {
        System.out.println("Calculating optimal solution for Scenario 2 using Brute Force...");
        long startTime = System.nanoTime();
        stateSpaceCounter = 0;

        int maxWolves = 0;
        List<int[]> bestSheep = new ArrayList<>(sheepPositions);
        grid.resetGrid();

        // Try all sheep placements
        for (List<int[]> placement : generateCombinations(grid.getSize(), sheepPositions.size())) {
            grid.resetGrid();
            for (int[] pos : placement) {
                grid.placeEntity(pos[0], pos[1], 's');
            }
            int wolfCount = countSafeWolves();
            if (wolfCount > maxWolves) {
                maxWolves = wolfCount;
                bestSheep = new ArrayList<>(placement);
            }
        }

        // Place the best configuration of sheep and calculate remaining wolves
        grid.resetGrid();
        for (int[] pos : bestSheep) {
            grid.placeEntity(pos[0], pos[1], 's');
        }
        placeAllSafeWolves();

        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("State Spaces Explored: " + stateSpaceCounter);
        displayResult();
    }

    private void placeAllSafeSheep() {
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.isSafeForSheep(i, j)) {
                    grid.placeEntity(i, j, 's');
                }
            }
        }
    }

    private void placeAllSafeWolves() {
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.isSafeForWolf(i, j)) {
                    grid.placeEntity(i, j, 'w');
                }
            }
        }
    }

    private int countSafeSheep() {
        int count = 0;
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.isSafeForSheep(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private int countSafeWolves() {
        int count = 0;
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.isSafeForWolf(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private List<List<int[]>> generateCombinations(int gridSize, int count) {
        // Generate all possible combinations of 'count' positions in the grid
        List<List<int[]>> combinations = new ArrayList<>();
        generateRecursive(0, gridSize, count, new ArrayList<>(), combinations);
        return combinations;
    }

    private void generateRecursive(int index, int gridSize, int count, List<int[]> current, List<List<int[]>> result) {
        stateSpaceCounter++;
        if (current.size() == count) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (index >= gridSize * gridSize) return;

        int row = index / gridSize;
        int col = index % gridSize;
        current.add(new int[]{row, col});
        generateRecursive(index + 1, gridSize, count, current, result);
        current.remove(current.size() - 1);
        generateRecursive(index + 1, gridSize, count, current, result);
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