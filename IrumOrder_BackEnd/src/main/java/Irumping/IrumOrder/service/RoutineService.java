package Irumping.IrumOrder.service;

import Irumping.IrumOrder.dto.MenuDetailDto;
import Irumping.IrumOrder.dto.RoutineDto;
import Irumping.IrumOrder.dto.RoutineResponseDto;
import Irumping.IrumOrder.entity.MenuDetailEntity;
import Irumping.IrumOrder.entity.RoutineEntity;
import Irumping.IrumOrder.exception.CustomExceptions.InvalidInputException;
import Irumping.IrumOrder.exception.CustomExceptions.InvalidRoutineException;
import Irumping.IrumOrder.exception.CustomExceptions.UserIdMismatchException;
import Irumping.IrumOrder.repository.JpaMenuRepository;
import Irumping.IrumOrder.repository.MenuDetailRepository;
import Irumping.IrumOrder.repository.MenuRepository;
import Irumping.IrumOrder.repository.RoutineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 클래스 설명: 루틴 관리 서비스 클래스.
 * 루틴 생성, 조회, 수정, 삭제와 관련된 비즈니스 로직을 처리한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-09
 */
@Service
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final JpaMenuRepository menuRepository;
    @Autowired
    private MenuDetailRepository menuDetailRepository;

    @Autowired
    public RoutineService(RoutineRepository routineRepository, JpaMenuRepository menuRepository) {
        this.routineRepository = routineRepository;
        this.menuRepository = menuRepository;
    }

    /**
     * 사용자의 모든 루틴을 조회하는 메서드.
     *
     * @param userId 사용자 ID
     * @return List<RoutineEntity> 해당 사용자의 루틴 리스트
     */
    public List<RoutineResponseDto> getRoutinesByUserId(Integer userId) {
        List<RoutineEntity> routines =  routineRepository.findByUserId(userId);
        return routines.stream().map(routine -> {
            MenuDetailEntity menuDetail = menuDetailRepository.findById(routine.getMenuDetailId());

            MenuDetailDto menuDetailDto = convertToMenuDetailDto(menuDetail);

            return new RoutineResponseDto(
                    routine.getRoutineId(),
                    routine.getUserId(),
                    routine.getMenuId(),
                    menuRepository.findMenuById(routine.getMenuId()).getName(),
                    routine.getMenuDetailId(),
                    menuDetailDto,
                    RoutineDayUtils.fromBitmask(routine.getRoutineDayBitmask()),
                    routine.getRoutineTime(),
                    routine.getAlarmEnabled()
            );
        }).collect(Collectors.toList());
    }


    /**
     * 새로운 루틴을 추가하는 메서드.
     *
     * @param routineDto 루틴 생성 요청 데이터
     * @return RoutineEntity 생성된 루틴 엔터티
     * @throws InvalidInputException 요청 데이터가 유효하지 않을 경우 발생
     */
    @Transactional
    public RoutineResponseDto addRoutine(RoutineDto routineDto) {
        RoutineEntity routine = new RoutineEntity();
        routine.setUserId(routineDto.getUserId());
        if(routineDto.getMenuOptions() == null){
            throw new InvalidInputException("Menu detail id is required.");
        }
        if(routineDto.getMenuId() == null){
            throw new InvalidInputException("Menu id is required.");
        }
        if(routineDto.getRoutineDays() == null){
            throw new InvalidInputException("Routine Day is required.");
        }
        if(routineDto.getRoutineTime() == null){
            throw new InvalidInputException("Routine Time is required.");
        }
        if(routineDto.getIsActivated() == null){
            throw new InvalidInputException("Alarm on/off value is required.");
        }
        routine.setMenuId(routineDto.getMenuId());
        routine.setRoutineTime(routineDto.getRoutineTime());
        routine.setAlarmEnabled(routineDto.getIsActivated());
        routine.setRoutineDayBitmask(RoutineDayUtils.toBitmask(routineDto.getRoutineDays()));
        MenuDetailEntity menuDetailEntity = new MenuDetailEntity(
                routineDto.getMenuOptions().getUseCup(),
                routineDto.getMenuOptions().getAddShot(),
                routineDto.getMenuOptions().getAddVanilla(),
                routineDto.getMenuOptions().getAddHazelnut(),
                routineDto.getMenuOptions().getLight()
        );
        menuDetailRepository.save(menuDetailEntity);

        routine.setMenuDetailId(menuDetailEntity.getMenuDetailId());
        routineRepository.save(routine);

        MenuDetailDto menuDetailDto = convertToMenuDetailDto(menuDetailEntity);

        return new RoutineResponseDto(
                routine.getRoutineId(),
                routine.getUserId(),
                routine.getMenuId(),
                menuRepository.findMenuById(routine.getMenuId()).getName(),
                menuDetailEntity.getMenuDetailId(),
                menuDetailDto,
                RoutineDayUtils.fromBitmask(routine.getRoutineDayBitmask()),
                routine.getRoutineTime(),
                routine.getAlarmEnabled()
        );
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
    public RoutineResponseDto updateRoutine(Integer routineId, RoutineDto routineDto) {
        RoutineEntity routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new InvalidRoutineException("Routine not found with ID: " + routineId));

        if (!Objects.equals(routine.getUserId(), routineDto.getUserId())) {
            throw new UserIdMismatchException("User ID in request does not match the authenticated user ID.");
        }

        Optional.ofNullable(routineDto.getMenuId()).ifPresent(routine::setMenuId);
        Optional.ofNullable(routineDto.getRoutineTime()).ifPresent(routine::setRoutineTime);
        Optional.ofNullable(routineDto.getIsActivated()).ifPresent(routine::setAlarmEnabled);
        Optional.ofNullable(routineDto.getRoutineDays())
                .ifPresent(days -> routine.setRoutineDayBitmask(RoutineDayUtils.toBitmask(days)));
        if (routineDto.getMenuOptions() != null) {
            MenuDetailEntity menuDetailEntity = new MenuDetailEntity(
                    routineDto.getMenuOptions().getUseCup(),
                    routineDto.getMenuOptions().getAddShot(),
                    routineDto.getMenuOptions().getAddVanilla(),
                    routineDto.getMenuOptions().getAddHazelnut(),
                    routineDto.getMenuOptions().getLight()
            );
            menuDetailRepository.save(menuDetailEntity);
            routine.setMenuDetailId(menuDetailEntity.getMenuDetailId());
        }
        routineRepository.save(routine);

        MenuDetailEntity menuDetail = menuDetailRepository.findById(routine.getMenuDetailId());

        MenuDetailDto menuDetailDto = convertToMenuDetailDto(menuDetail);

        return new RoutineResponseDto(
                routine.getRoutineId(),
                routine.getUserId(),
                routine.getMenuId(),
                menuRepository.findMenuById(routine.getMenuId()).getName(),
                routine.getMenuDetailId(),
                menuDetailDto,
                RoutineDayUtils.fromBitmask(routine.getRoutineDayBitmask()),
                routine.getRoutineTime(),
                routine.getAlarmEnabled()
        );
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
    public void deleteRoutine(Integer routineId, Integer userId) {
        // 루틴 존재 여부 확인 후 삭제
        RoutineEntity routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new InvalidRoutineException("Routine not found with ID: " + routineId));
        if(!Objects.equals(routine.getUserId(), userId)){
            throw new UserIdMismatchException("User ID in request does not match the authenticated user ID.");
        }
        routineRepository.delete(routine);
    }

    private MenuDetailDto convertToMenuDetailDto(MenuDetailEntity menuDetail) {
        return new MenuDetailDto(
                menuDetail.getUseCup(),
                menuDetail.isAddShot(),
                menuDetail.isAddVanilla(),
                menuDetail.isAddHazelnut(),
                menuDetail.isLight()
        );
    }
}