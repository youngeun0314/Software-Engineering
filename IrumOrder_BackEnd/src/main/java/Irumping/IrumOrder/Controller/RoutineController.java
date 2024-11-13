package Irumping.IrumOrder.Controller;


import Irumping.IrumOrder.Dto.RoutineDto;
import Irumping.IrumOrder.Entity.RoutineDay;
import Irumping.IrumOrder.Entity.RoutineEntity;
import Irumping.IrumOrder.Service.RoutineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;


@RestController
@RequestMapping("/api/routines")
public class RoutineController {

    private final RoutineService routineService;

    @Autowired
    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @PostMapping
    public ResponseEntity<RoutineEntity> createRoutine(@RequestParam Integer userId,
                                                 @RequestParam Integer menuId,
                                                 @RequestParam(required = false) Integer menuDetailId,
                                                 @RequestParam RoutineDay routineDay,
                                                 @RequestParam LocalTime routineTime,
                                                 @RequestParam(required = false) Integer price) {
        RoutineEntity routine = routineService.addRoutine(userId, menuId, menuDetailId, routineDay, routineTime, price);
        return ResponseEntity.ok(routine);
    }
}