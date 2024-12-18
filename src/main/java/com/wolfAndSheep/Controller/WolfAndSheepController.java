package com.wolfAndSheep.Controller;

import com.wolfAndSheep.Model.EntityPosition;
import com.wolfAndSheep.Model.Grid;
import com.wolfAndSheep.Model.ScenarioRequest;
import com.wolfAndSheep.Service.BacktrackingSolver;
import com.wolfAndSheep.Service.BruteForceSolver;
import com.wolfAndSheep.Service.OptimalBacktrackingSolver;
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


        for (EntityPosition entity : request.getInitialPositions()) {
            System.out.println(entity.getRow()+" - "+entity.getCol()+" - "+entity.getType());
            grid.placeEntity(entity.getRow(), entity.getCol(), entity.getType());
        }

        if ("bruteforce".equalsIgnoreCase(request.getSolver())) {
            BruteForceSolver solver = new BruteForceSolver(grid);

            if (request.getScenario() == 1) {
                solver.solveScenario1();
            } else if (request.getScenario() == 2) {
                solver.solveScenario2();
            }
            stateSpacesExplored = solver.getStateSpaceCounter();
        } else if ("backtracking".equalsIgnoreCase(request.getSolver())) {
            BacktrackingSolver solver = new BacktrackingSolver(grid);
            if (request.getScenario() == 1) {
                solver.solveScenario1();
            } else if (request.getScenario() == 2) {
                solver.solveScenario2();
            }
            stateSpacesExplored = solver.getStateSpaceCounter();

        }else if ("optimal".equalsIgnoreCase(request.getSolver())) {
            OptimalBacktrackingSolver solver = new OptimalBacktrackingSolver(grid);
            if (request.getScenario() == 1) {
                solver.solveScenario1(grid.getCoordinates('w'));
            } else if (request.getScenario() == 2) {
                solver.solveScenario2(grid.getCoordinates('s'));
            }
            stateSpacesExplored = solver.getStateSpaceCounter();

        }


        Map<String, Object> response = new HashMap<>();
        response.put("gridSize", request.getGridSize());
        response.put("scenario", request.getScenario());
        response.put("solver", request.getSolver());
        response.put("wolves", grid.getCoordinates('w'));
        response.put("sheep", grid.getCoordinates('s'));
        response.put("state_space_explored: ", stateSpacesExplored);

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
