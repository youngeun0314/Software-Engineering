package Irumping.IrumOrder.Repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    public String Password(String id);

    public void save(String id, String password, String email, String name);
}
