package Irumping.IrumOrder.service;

import Irumping.IrumOrder.dto.RoutineDto;
import Irumping.IrumOrder.entity.RoutineEntity;
import Irumping.IrumOrder.exeption.InvalidInputException;
import Irumping.IrumOrder.exeption.InvalidRoutineExceiption;
import Irumping.IrumOrder.exeption.UserIdMismatchException;
import Irumping.IrumOrder.repository.RoutineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Autowired
    public RoutineService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    public List<RoutineEntity> getRoutinesByUserId(long userId) {
        return routineRepository.findByUserId(userId);
    }

    @Transactional
    public RoutineEntity addRoutine(RoutineDto routineDto) {
        RoutineEntity routine = new RoutineEntity();
        routine.setUserId(routineDto.getUserId());
        if(routineDto.getMenuDetailId() == null){
            throw new InvalidInputException("Menu detail id is required.");
        }
        if(routineDto.getMenuId() == null){
            throw new InvalidInputException("Menu id is required.");
        }
        if(routineDto.getRoutineDay() == null){
            throw new InvalidInputException("Routine Day is required.");
        }
        if(routineDto.getRoutineTime() == null){
            throw new InvalidInputException("Routine Time is required.");
        }
        if(routineDto.getIsActivated() == null){
            throw new InvalidInputException("Alarm on/off value is required.");
        }
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
                .orElseThrow(() -> new InvalidRoutineExceiption("Routine not found with ID: " + routineId));

        if (routine.getUserId() != routineDto.getUserId()) {
            throw new UserIdMismatchException("User ID in request does not match the authenticated user ID.");
        }

        Optional.ofNullable(routineDto.getMenuId()).ifPresent(routine::setMenuId);
        Optional.ofNullable(routineDto.getMenuDetailId()).ifPresent(routine::setMenuDetailId);
        Optional.ofNullable(routineDto.getRoutineDay()).ifPresent(routine::setRoutineDay);
        Optional.ofNullable(routineDto.getRoutineTime()).ifPresent(routine::setRoutineTime);
        Optional.ofNullable(routineDto.getIsActivated()).ifPresent(routine::setAlarmEnabled);

        return routineRepository.save(routine);
    }


    @Transactional
    public void deleteRoutine(Integer routineId, long userId) {
        // 루틴 존재 여부 확인 후 삭제
        RoutineEntity routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new InvalidRoutineExceiption("Routine not found with ID: " + routineId));
        if(routine.getUserId() != userId){
            throw new UserIdMismatchException("User ID in request does not match the authenticated user ID.");
        }
        routineRepository.delete(routine);
    }
}