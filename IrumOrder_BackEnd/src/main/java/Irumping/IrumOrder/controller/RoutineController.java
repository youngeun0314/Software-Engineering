package Irumping.IrumOrder.controller;


import Irumping.IrumOrder.dto.RoutineDto;
import Irumping.IrumOrder.entity.RoutineEntity;
import Irumping.IrumOrder.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users/{userId}/routines")
public class RoutineController {

    private final RoutineService routineService;

    @Autowired
    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping
    public ResponseEntity<List<RoutineEntity>> getAllRoutinesByUserId(@PathVariable int userId) {
        List<RoutineEntity> routines = routineService.getRoutinesByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(routines);
    }

    @PostMapping("/add")
    public ResponseEntity<RoutineEntity> createRoutine(
            @RequestBody RoutineDto routineDto,
            @PathVariable long userId) {
        RoutineEntity createdRoutine = routineService.addRoutine(routineDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoutine);
    }


    // 루틴 수정 메서드
    @PutMapping("/{routineId}")
    public ResponseEntity<RoutineEntity> updateRoutine(
            @PathVariable Integer routineId,
            @RequestBody RoutineDto routineDto) {

        RoutineEntity updatedRoutine = routineService.updateRoutine(routineId, routineDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRoutine);
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