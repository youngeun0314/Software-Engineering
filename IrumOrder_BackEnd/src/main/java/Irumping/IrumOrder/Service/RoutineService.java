package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Dto.RoutineDto;
import Irumping.IrumOrder.Entity.RoutineDay;
import Irumping.IrumOrder.Entity.RoutineEntity;
import Irumping.IrumOrder.Repository.RoutineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;


@Service
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Autowired
    public RoutineService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    @Transactional
    public RoutineEntity addRoutine(Integer userId, Integer menuId, Integer menuDetailId, RoutineDay routineDay, LocalTime routineTime, Integer price) {
        RoutineEntity routine = new RoutineEntity();
        routine.setUserId(userId);
        routine.setMenuId(menuId);
        routine.setMenuDetailId(menuDetailId);
        routine.setRoutineDay(routineDay);
        routine.setRoutineTime(routineTime);
        routine.setPrice(price);

        return routineRepository.save(routine);
    }
}