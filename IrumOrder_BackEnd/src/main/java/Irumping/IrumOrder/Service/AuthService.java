package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Entity.UserEntity;
import Irumping.IrumOrder.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository repository;

    public void signUp(String id, String password, String email) {
        // validator를 여기에 넣을지 고민
        UserEntity user = new UserEntity(id, hashPassword(password), email);
        repository.save(user.getId(), user.getPassword(), user.getEmail());
    }

    public boolean login(String id, String password) {
        String dbPassword = repository.getPassword(id);

        if (dbPassword == null) {
            // 아이디가 없음
            return false;
        }
        if (dbPassword.equals(hashPassword(password))) {
            // 로그인 성공
            return true;
        } else {
            // 로그인 실패
            return false;
        }
    }

    private String hashPassword(String password) {
        // password 해싱 로직
        return password;
    }
}
