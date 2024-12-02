package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.RoutineEntity;
import Irumping.IrumOrder.entity.RoutineDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<RoutineEntity, Integer> {
    List<RoutineEntity> findByRoutineDayAndAlarmEnabled(RoutineDay routineDay, Boolean alarmEnabled);
}
