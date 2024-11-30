package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(Long userId);
    public String Password(String id);

    public void save(String id, String password, String email, String name);
}
