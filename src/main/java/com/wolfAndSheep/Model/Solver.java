package com.wolfAndSheep.Model;

public class Solver {
    private final int size;
    private final char[][] grid;

    public Solver(int size, char[][] grid) {
        this.size = size;
        this.grid = grid;
    }

    public void placeSafeSheep() {
        boolean safePositionFound = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == '_' && isSafeFromWolves(i, j)) {
                    grid[i][j] = 's';
                    safePositionFound = true;
                }
            }
        }
        if (!safePositionFound) {
            System.out.println("No safe configuration possible for sheep.");
        }
    }

    public void placeSafeWolves() {
        boolean safePositionFound = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == '_' && isSafeFromSheep(i, j)) {
                    grid[i][j] = 'w';
                    safePositionFound = true;
                }
            }
        }
        if (!safePositionFound) {
            System.out.println("No safe configuration possible for wolves.");
        }
    }

    private boolean isSafeFromWolves(int row, int col) {
        for (int i = 0; i < size; i++) {
            if (grid[row][i] == 'w' || grid[i][col] == 'w') return false;
        }
        for (int i = -size; i < size; i++) {
            if (inBounds(row + i, col + i) && grid[row + i][col + i] == 'w') return false;
            if (inBounds(row + i, col - i) && grid[row + i][col - i] == 'w') return false;
        }
        return true;
    }

    private boolean isSafeFromSheep(int row, int col) {
        for (int i = 0; i < size; i++) {
            if (grid[row][i] == 's' || grid[i][col] == 's') return false;
        }
        for (int i = -size; i < size; i++) {
            if (inBounds(row + i, col + i) && grid[row + i][col + i] == 's') return false;
            if (inBounds(row + i, col - i) && grid[row + i][col - i] == 's') return false;
        }
        return true;
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }
}
