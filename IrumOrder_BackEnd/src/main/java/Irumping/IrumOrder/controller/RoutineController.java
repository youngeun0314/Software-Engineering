package Irumping.IrumOrder.controller;


import Irumping.IrumOrder.dto.RoutineDto;
import Irumping.IrumOrder.entity.RoutineEntity;
import Irumping.IrumOrder.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users/{userId}/routines")
public class RoutineController {

    private final RoutineService routineService;

    @Autowired
    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping
    public ResponseEntity<List<RoutineEntity>> getAllRoutinesByUserId(@PathVariable int userId) {
        List<RoutineEntity> routines = routineService.getRoutinesByUserId(userId);
        return ResponseEntity.ok(routines);
    }

    @PostMapping("/add")
    public RoutineEntity createRoutine(
            @RequestBody RoutineDto routineDto,
            @PathVariable long userId) {
        return routineService.addRoutine(routineDto, userId);
    }


    // 루틴 수정 메서드
    @PutMapping("/{routineId}")
    public ResponseEntity<RoutineEntity> updateRoutine(
            @PathVariable Integer routineId,
            @RequestBody RoutineDto routineDto) {

        RoutineEntity updatedRoutine = routineService.updateRoutine(routineId, routineDto);
        return ResponseEntity.ok(updatedRoutine);
    }

    //루틴 삭제 메서드
    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deleteRoutine(
            @PathVariable Integer routineId,
            @PathVariable long userId) {
        routineService.deleteRoutine(routineId, userId);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }

}