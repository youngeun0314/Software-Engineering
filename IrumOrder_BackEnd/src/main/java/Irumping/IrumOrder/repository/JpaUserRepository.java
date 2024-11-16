package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    @Override
    public void save(String userId, String password, String email) {
        UserEntity user = new UserEntity(userId, password, email);
        em.persist(user);
    }

    @Override
    public boolean isExist(String userId) {
        // jpql
        return em.createQuery("select count(u) from UserEntity u where u.userId = :userId", Long.class)
                .setParameter("userId", userId)
                .getSingleResult() > 0;
    }

    @Override
    public String getPassword(String userId) {
        if (!isExist(userId)) {
            return null;
        }
        return em.createQuery("select u.password from UserEntity u where u.userId = :userId", String.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
