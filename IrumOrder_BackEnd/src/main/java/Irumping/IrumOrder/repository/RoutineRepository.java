package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineRepository extends JpaRepository<RoutineEntity, Integer> {
    List<RoutineEntity> findByUserId(long userId);
}