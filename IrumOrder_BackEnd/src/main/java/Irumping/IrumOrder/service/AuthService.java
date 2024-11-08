package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.UserEntity;
import Irumping.IrumOrder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository repository;

    public void signUp(String id, String password, String email) {
        try {
            String hashedPassword = hashPassword(password);
            UserEntity user = new UserEntity(id, hashedPassword, email);
            repository.save(user.getId(), user.getPassword(), user.getEmail());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("비밀번호 해싱 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("회원 가입 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", e);
        }
    }

    public boolean login(String id, String password) {
        String dbPassword = repository.getPassword(id);

        // token 발급
        // sessionRepository에 token 저장
        // token 저장 저장소 필요하겠죠


        // filter/interceptor에서 token 검증 -> 인증된 사용자인지 구분
        // 이에 따라, 접근 권한 부여
        // 말그대로, sign_up or login만 접근 가능한 사용자인지,
        // 혹은 로그인해서 다 접근 되는 사용자인지 구분하는 로직


        // token이 만료/갱신되는 정책 이런것도 설정
        // 음 앱 사용자면 만료가 필요가없나...몰라

        if (dbPassword == null) {
            // 아이디가 없음
            return false;
        }
        try {
            if (verifyPassword(password, dbPassword)) {
                // 로그인 성공
                return true;
            } else {
                // 로그인 실패
                return false;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("비밀번호 검증 중 오류 발생: {}", e.getMessage());
            return false;
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 10000;
        byte[] salt = getSalt();
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, 512);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");    // PBKDF2 알고리즘 사용 : 해시함수를 여러번 적용하여 해시값을 만들어내는 방식
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);   // salt와 hash를 구분자로 구분
    }

    // salt는 해시값을 만들 때 사용하는 임의의 바이트 배열  // 무작위 salt 생성
    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private boolean verifyPassword(String password, String storedHash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedHash.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] hash = Base64.getDecoder().decode(parts[1]);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 512);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        return java.util.Arrays.equals(hash, testHash);
    }
}
