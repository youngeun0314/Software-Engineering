package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    @Override
    public void save(String id, String password, String email) {
        UserEntity user = new UserEntity(id, password, email);
        em.persist(user);
    }

    @Override
    public boolean isExist(String id) {
        // jpql
        return em.createQuery("select count(u) > 0 from UserEntity u where u.id = :id", Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public String getPassword(String id) {
        if (!isExist(id)) {
            return null;
        }
        return em.createQuery("select u.password from UserEntity u where u.id = :id", String.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Optional<String> getUserMode(Long userId) {
        try {
            return Optional.ofNullable(
                    em.createQuery("select u.mode from UserEntity u where u.id = :userId", String.class)
                            .setParameter("userId", userId)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
