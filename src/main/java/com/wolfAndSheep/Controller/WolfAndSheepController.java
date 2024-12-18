package com.wolfAndSheep.Controller;

import com.wolfAndSheep.Model.EntityPosition;
import com.wolfAndSheep.Model.Grid;
import com.wolfAndSheep.Model.ScenarioRequest;
import com.wolfAndSheep.Service.BacktrackingSolver;
import com.wolfAndSheep.Service.BruteForceSolver;
import com.wolfAndSheep.Service.OptimalBacktrackingSolver;
import com.wolfAndSheep.Service.OptimalBruteForceSolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wolf-sheep")
public class WolfAndSheepController {

    @PostMapping("/solve-scenario")
    public ResponseEntity<Map<String, Object>> solveScenario(@RequestBody ScenarioRequest request) {

        Grid grid = new Grid(request.getGridSize());

        int stateSpacesExplored = 0;




        if ("bruteforce".equalsIgnoreCase(request.getSolver())) {
            OptimalBruteForceSolver solver = new OptimalBruteForceSolver(grid);

            if (request.getScenario() == 0) {
                solver.solveScenario1(request.getNumberAnimals());
            }
            stateSpacesExplored = solver.getStateSpaceCounter();
        } else if ("backtracking".equalsIgnoreCase(request.getSolver())) {
            OptimalBacktrackingSolver solver = new OptimalBacktrackingSolver(grid);
            if (request.getScenario() == 0) {
                solver.solveScenario1(request.getNumberAnimals());
            }
            stateSpacesExplored = solver.getStateCounter();

        }


        Map<String, Object> response = new HashMap<>();
        response.put("gridSize", request.getGridSize());
        response.put("scenario", request.getScenario());
        response.put("solver", request.getSolver());
        response.put("wolves", grid.getCoordinates('w'));
        response.put("sheep", grid.getCoordinates('s'));
        response.put("state_space_explored", stateSpacesExplored);

        return ResponseEntity.ok(response);
    }

    private String formatCoordinates(List<int[]> coordinates) {
        StringBuilder sb = new StringBuilder();
        for (int[] coord : coordinates) {
            sb.append("(").append(coord[0]).append(", ").append(coord[1]).append(") ");
        }
        return sb.toString();
    }
}
