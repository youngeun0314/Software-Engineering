package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.RoutineEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
    public List<RoutineEntity> findByUserId(Integer userId) {
        return em.createQuery("SELECT r FROM RoutineEntity r WHERE r.userId = :userId", RoutineEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }


    @Override
    public void save(RoutineEntity routine) {
        if (routine.getRoutineId() == null) { // New entity
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

    @Override
    public List<RoutineEntity> findAll() {
        TypedQuery<RoutineEntity> query = em.createQuery(
                "SELECT r FROM RoutineEntity r", RoutineEntity.class);
        return query.getResultList();
    }
}
