package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MockLoginRepository implements UserRepository {

    private Map<String, UserEntity> mockDB = new HashMap<>();

    @Override
    public void save(String id, String password, String email, String name) {
        mockDB.put(id, new UserEntity(id, password, email, name));
    }

    @Override
    public String Password(String id) {
        return mockDB.get(id).getPassword();
    }


}
