package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepository extends JpaRepository<RoutineEntity, Integer> {
}