package Irumping.IrumOrder.controller;


import Irumping.IrumOrder.dto.RoutineDto;
import Irumping.IrumOrder.entity.RoutineDay;
import Irumping.IrumOrder.entity.RoutineEntity;
import Irumping.IrumOrder.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;


@RestController
@RequestMapping("/routines")
public class RoutineController {

    private final RoutineService routineService;

    @Autowired
    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }



    @PostMapping("/add")
    public RoutineEntity createRoutine(@RequestBody RoutineDto routineDto) {
        return routineService.addRoutine(routineDto);
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
    public ResponseEntity<Void> deleteRoutine(@PathVariable Integer routineId) {
        routineService.deleteRoutine(routineId);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }

}