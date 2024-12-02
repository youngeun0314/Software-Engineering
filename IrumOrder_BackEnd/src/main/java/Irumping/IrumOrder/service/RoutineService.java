package Irumping.IrumOrder.service;

import Irumping.IrumOrder.dto.RoutineDto;
import Irumping.IrumOrder.entity.RoutineEntity;
import Irumping.IrumOrder.exception.CustomExceptions.InvalidInputException;
import Irumping.IrumOrder.exception.CustomExceptions.InvalidRoutineException;
import Irumping.IrumOrder.exception.CustomExceptions.UserIdMismatchException;
import Irumping.IrumOrder.repository.RoutineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * 클래스 설명: 루틴 관리 서비스 클래스.
 * 루틴 생성, 조회, 수정, 삭제와 관련된 비즈니스 로직을 처리한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-02
 */
@Service
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Autowired
    public RoutineService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    /**
     * 사용자의 모든 루틴을 조회하는 메서드.
     *
     * @param userId 사용자 ID
     * @return List<RoutineEntity> 해당 사용자의 루틴 리스트
     */
    public List<RoutineEntity> getRoutinesByUserId(long userId) {
        return routineRepository.findByUserId(userId);
    }


    /**
     * 새로운 루틴을 추가하는 메서드.
     *
     * @param routineDto 루틴 생성 요청 데이터
     * @return RoutineEntity 생성된 루틴 엔터티
     * @throws InvalidInputException 요청 데이터가 유효하지 않을 경우 발생
     */
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

        routineRepository.save(routine);
        return routine;
    }

    /**
     * 기존 루틴을 수정하는 메서드.
     *
     * @param routineId 수정할 루틴 ID
     * @param routineDto 수정할 루틴 데이터
     * @return RoutineEntity 수정된 루틴 엔터티
     * @throws InvalidRoutineException 해당 ID의 루틴이 존재하지 않을 경우 발생
     * @throws UserIdMismatchException 요청 사용자 ID와 루틴 소유자의 ID가 다를 경우 발생
     */
    @Transactional
    public RoutineEntity updateRoutine(Integer routineId, RoutineDto routineDto) {
        RoutineEntity routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new InvalidRoutineException("Routine not found with ID: " + routineId));

        if (routine.getUserId() != routineDto.getUserId()) {
            throw new UserIdMismatchException("User ID in request does not match the authenticated user ID.");
        }

        Optional.ofNullable(routineDto.getMenuId()).ifPresent(routine::setMenuId);
        Optional.ofNullable(routineDto.getMenuDetailId()).ifPresent(routine::setMenuDetailId);
        Optional.ofNullable(routineDto.getRoutineDay()).ifPresent(routine::setRoutineDay);
        Optional.ofNullable(routineDto.getRoutineTime()).ifPresent(routine::setRoutineTime);
        Optional.ofNullable(routineDto.getIsActivated()).ifPresent(routine::setAlarmEnabled);

        routineRepository.save(routine);
        return routine;
    }


    /**
     * 루틴을 삭제하는 메서드.
     *
     * @param routineId 삭제할 루틴 ID
     * @param userId 요청 사용자 ID
     * @throws InvalidRoutineException 해당 ID의 루틴이 존재하지 않을 경우 발생
     * @throws UserIdMismatchException 요청 사용자 ID와 루틴 소유자의 ID가 다를 경우 발생
     */
    @Transactional
    public void deleteRoutine(Integer routineId, long userId) {
        // 루틴 존재 여부 확인 후 삭제
        RoutineEntity routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new InvalidRoutineException("Routine not found with ID: " + routineId));
        if(routine.getUserId() != userId){
            throw new UserIdMismatchException("User ID in request does not match the authenticated user ID.");
        }
        routineRepository.delete(routine);
    }
}