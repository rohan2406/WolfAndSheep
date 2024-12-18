package com.wolfAndSheep.Model;

import java.util.List;

public class ScenarioRequest {
    private int gridSize;
    private int scenario;

    public int getNumberAnimals() {
        return numberAnimals;
    }

    public void setNumberAnimals(int numberAnimals) {
        this.numberAnimals = numberAnimals;
    }

    private int numberAnimals;

    private String solver;
    private List<EntityPosition> initialPositions;

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getScenario() {
        return scenario;
    }

    public void setScenario(int scenario) {
        this.scenario = scenario;
    }

    public String getSolver() {
        return solver;
    }

    public void setSolver(String solver) {
        this.solver = solver;
    }
}
