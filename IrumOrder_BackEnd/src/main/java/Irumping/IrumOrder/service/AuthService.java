package Irumping.IrumOrder.service;

import Irumping.IrumOrder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository repository;

    @Transactional
    public void signUp(String id, String password, String email) {
        repository.save(id, hashPassword(password), email);
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
        return verifyPassword(password, dbPassword);
    }

    // 아이디 중복 체크
    public boolean isExist(String id) {
        return repository.isExist(id);
    }

    private String hashPassword(String password) {
        try {
            int iterations = 10000;
            byte[] salt = getSalt();
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, 512);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");    // PBKDF2 알고리즘 사용 : 해시함수를 여러번 적용하여 해시값을 만들어내는 방식
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);   // salt와 hash를 구분자로 구분
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("비밀번호 해싱 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("비밀번호 해싱 중 오류 발생", e);
        }
    }

    // salt는 해시값을 만들 때 사용하는 임의의 바이트 배열  // 무작위 salt 생성
    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private boolean verifyPassword(String password, String storedHash) {
        try {
            // 저장된 salt와 hash 분리
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("유효하지 않은 저장된 해시값입니다.");
            }
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] originalHash = Base64.getDecoder().decode(parts[1]);

            // 입력된 비밀번호를 동일한 salt로 해싱
            int iterations = 10000;
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, 512);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] computedHash = skf.generateSecret(spec).getEncoded();

            // 원래 해시와 비교
            return Arrays.equals(originalHash, computedHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("비밀번호 검증 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("비밀번호 검증 중 오류 발생", e);
        }
    }
}
