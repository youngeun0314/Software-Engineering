package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MockLoginRepository implements UserRepository {

    private Map<String, UserEntity> mockDB = new HashMap<>();

    @Override
    public void save(String id, String password, String email) {
        mockDB.put(id, new UserEntity(id, password, email));
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return false;
    }

    @Override
    public String getPassword(String id) {
        if (!mockDB.containsKey(id)) {
            return null;
        }
        return mockDB.get(id).getPassword();
    }


}
