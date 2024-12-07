package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 클래스 설명: 사용자 정보를 DB에 저장하고 조회하는 클래스
 * 사용자 정보를 DB에 저장하고 조회하는 기능을 제공
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
@RequiredArgsConstructor
@Repository
public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    /**
     * 사용자 정보를 DB에 저장하는 메소드
     * 사용자의 아이디, 비밀번호, 이메일 정보를 DB에 저장
     *
     * @param id 사용자 아이디
     * @param password 사용자 비밀번호
     * @param email 사용자 이메일
     */
    @Override
    public void save(String id, String password, String email) {
        UserEntity user = new UserEntity(id, password, email);
        em.persist(user);
    }

    /**
     * 사용자 아이디가 DB에 이미 존재하는지 확인하는 메소드
     *
     * @param id 사용자 아이디
     * @return 사용자 아이디가 이미 존재하면 true, 존재하지 않으면 false
     */
    @Override
    public boolean isExist(String id) {
        // jpql
        return em.createQuery("select count(u) > 0 from UserEntity u where u.id = :id", Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    /**
     * 사용자 비밀번호를 DB에서 조회하는 메소드
     *
     * @param id 사용자 아이디
     * @return 사용자 비밀번호
     */
    @Override
    public String getPassword(String id) {
        if (!isExist(id)) {
            return null;
        }
        return em.createQuery("select u.password from UserEntity u where u.id = :id", String.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    /**
     * 사용자 아이디로 사용자 정보를 조회하는 메소드
     *
     * @param id 사용자 아이디
     * @return Optional로 감싼 UserEntity 객체.
     *         해당 아이디의 사용자가 존재하지 않을 경우 Optional.empty() 반환.
     */

    @Override
    public Optional<UserEntity> findById(String id) {
        UserEntity user = em.createQuery(
                        "SELECT u FROM UserEntity u WHERE u.id = :id", UserEntity.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<UserEntity> findByUserId(Integer userId) {
        return Optional.ofNullable(em.find(UserEntity.class, userId));
    }
}
