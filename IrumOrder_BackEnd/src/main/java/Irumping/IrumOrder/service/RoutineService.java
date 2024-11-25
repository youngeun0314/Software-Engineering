package Irumping.IrumOrder.service;

import Irumping.IrumOrder.dto.RoutineDto;
import Irumping.IrumOrder.entity.RoutineDay;
import Irumping.IrumOrder.entity.RoutineEntity;
import Irumping.IrumOrder.exeption.UserIdMismatchException;
import Irumping.IrumOrder.repository.RoutineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;


@Service
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Autowired
    public RoutineService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    public List<RoutineEntity> getRoutinesByUserId(int userId) {
        return routineRepository.findByUserId(userId);
    }

    @Transactional
    public RoutineEntity addRoutine(RoutineDto routineDto, int userId) {
        if (userId!=(routineDto.getUserId())) {
            throw new UserIdMismatchException("User ID in request does not match the authenticated user ID.");
        }
        RoutineEntity routine = new RoutineEntity();
        routine.setUserId(userId);
        routine.setMenuId(routineDto.getMenuId());
        routine.setMenuDetailId(routineDto.getMenuDetailId());
        routine.setRoutineDay(routineDto.getRoutineDay());
        routine.setRoutineTime(routineDto.getRoutineTime());
        routine.setAlarmEnabled(routineDto.getIsActivated());

        return routineRepository.save(routine);
    }

    @Transactional
    public RoutineEntity updateRoutine(Integer routineId, RoutineDto routineDto) {
        RoutineEntity routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("Routine not found with ID: " + routineId));

        if (routineDto.getMenuId() != null) {
            routine.setMenuId(routineDto.getMenuId());
        }

        if (routineDto.getMenuDetailId() != null) {
            routine.setMenuDetailId(routineDto.getMenuDetailId());
        }

        if (routineDto.getRoutineDay() != null) {
            routine.setRoutineDay(routineDto.getRoutineDay());
        }

        if (routineDto.getRoutineTime() != null) {
            routine.setRoutineTime(routineDto.getRoutineTime());
        }

        if (routineDto.getIsActivated() != null) {
            routine.setAlarmEnabled(routineDto.getIsActivated());
        }

        return routineRepository.save(routine);
    }


    @Transactional
    public void deleteRoutine(Integer routineId) {
        // 루틴 존재 여부 확인 후 삭제
        RoutineEntity routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("Routine not found with ID: " + routineId));

        routineRepository.delete(routine);
    }
}