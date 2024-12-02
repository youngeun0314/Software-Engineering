package Irumping.IrumOrder.repository;

<<<<<<< HEAD
import Irumping.IrumOrder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(Long userId);
    public String Password(String id);

=======
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    public String Password(String id);

    public void save(String id, String password, String email, String name);
>>>>>>> 7f7bb4bb8ba8586161cc4e94df55a95add8ad11c
}
