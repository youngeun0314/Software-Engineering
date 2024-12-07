package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.RoutineDay;
import Irumping.IrumOrder.entity.RoutineEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaRoutineRepository implements RoutineRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<RoutineEntity> findByUserId(long userId) {
        return em.createQuery("SELECT r FROM RoutineEntity r WHERE r.userId = :userId", RoutineEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<RoutineEntity> findByRoutineDayAndAlarmEnabledTrue(RoutineDay routineDay) {
        return em.createQuery(
                        "SELECT r FROM RoutineEntity r WHERE r.routineDay = :routineDay AND r.alarmEnabled = true",
                        RoutineEntity.class)
                .setParameter("routineDay", routineDay)
                .getResultList();
    }

    @Override
    public void save(RoutineEntity routine) {
        if (routine.getRoutineId() == 0) { // New entity
            em.persist(routine);
        } else { // Existing entity
            em.merge(routine);
        }
    }

    @Override
    public Optional<RoutineEntity> findById(Integer routineId) {
        return Optional.ofNullable(em.find(RoutineEntity.class, routineId));
    }

    @Override
    public void delete(RoutineEntity routine) {
        em.remove(routine);
    }
}
