package com.wolfAndSheep.Model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private final int size;
    private final char[][] board;

    public Grid(int size) {
        this.size = size;
        this.board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '_';
            }
        }
    }

    public int getSize() {
        return size;
    }

    public char[][] getBoard() {
        return board;
    }

    public void placeEntity(int row, int col, char entity) {
        if (isValid(row, col)) {
            board[row][col] = entity;
        }
    }

    public boolean isValid(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public boolean isSafeForSheep(int row, int col) {
        for (int i = 0; i < size; i++) {
            if (board[row][i] == 'w' || board[i][col] == 'w') return false;
        }
        for (int i = -size; i < size; i++) {
            if (isValid(row + i, col + i) && board[row + i][col + i] == 'w') return false;
            if (isValid(row + i, col - i) && board[row + i][col - i] == 'w') return false;
        }
        return true;
    }

    public boolean isSafeForWolf(int row, int col) {
        // Ensure the wolf does not attack any existing sheep
        for (int i = 0; i < size; i++) {
            if (board[row][i] == 's' || board[i][col] == 's') return false;
        }
        for (int i = -size; i < size; i++) {
            if (isValid(row + i, col + i) && board[row + i][col + i] == 's') return false;
            if (isValid(row + i, col - i) && board[row + i][col - i] == 's') return false;
        }
        return board[row][col] == '_'; // Ensure the cell is empty
    }

    public char getEntityAt(int row, int col) {
        if (isValid(row, col)) {
            return board[row][col];
        } else {
            throw new IllegalArgumentException("Invalid grid coordinates: (" + row + ", " + col + ")");
        }
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public List<int[]> getCoordinates(char entity) {
        List<int[]> coordinates = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == entity) {
                    coordinates.add(new int[]{i, j});
                }
            }
        }
        return coordinates;
    }

    // Added resetGrid method
    public void resetGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '_';
            }
        }
    }

}