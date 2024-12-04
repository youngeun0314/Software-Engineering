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

/**
 * 클래스 설명: 사용자 인증 관련 기능을 제공하는 클래스
 * 사용자 등록, 로그인, 이메일 인증 등의 기능을 제공
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository repository;

    /**
     * 사용자 등록
     *
     * @param id 사용자 아이디
     * @param password 사용자 비밀번호
     * @param email 사용자 이메일
     */
    @Transactional
    public void signUp(String id, String password, String email) {
        repository.save(id, hashPassword(password), email);
    }

    /**
     * 사용자 로그인
     *
     * @param id 사용자 아이디
     * @param password 사용자 비밀번호
     * @return 로그인 성공 시 true, 실패 시 false
     */
    public boolean login(String id, String password) {
        String dbPassword = repository.getPassword(id);

        if (dbPassword == null) {
            // 아이디가 없음
            return false;
        }
        return verifyPassword(password, dbPassword);
    }

    /**
     * 아이디 중복 체크
     *
     * @param id 사용자 아이디
     * @return 아이디가 이미 존재하면 true, 존재하지 않으면 false
     */
    public boolean isExist(String id) {
        return repository.isExist(id);
    }

    /**
     * 비밀번호 해싱
     *
     * @param password 사용자 비밀번호
     * @return 해싱된 비밀번호
     */
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

    /**
     * 무작위 salt 생성
     * 해싱에 사용할 salt를 생성
     *
     * @return salt
     * @throws NoSuchAlgorithmException
     */
    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * 비밀번호 검증
     * 사용자가 입력한 비밀번호와 DB에 저장된 해시값을 비교하여 일치 여부 확인
     *
     * @param password 사용자가 입력한 비밀번호
     * @param storedHash DB에 저장된 해시값
     * @return 비밀번호 일치 시 true, 불일치 시 false
     */
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

    /**
     * 이메일 인증 코드 생성
     *
     * @return "1234"로 고정된 인증 코드
     */
    public String generateVerificationCode() {
        // 4자리 숫자로 이루어진 인증 코드 생성
//        return String.valueOf((int) (Math.random() * 9000) + 1000);
        return "1234";
    }

    /**
     * 이메일 인증 코드 전송
     *
     * @param email 사용자 이메일
     * @param code 인증 코드
     */
    public void sendVerificationEmail(String email, String code) {
        // 이메일 전송
    }

    /**
     * 이메일 인증 코드 검증
     * (인증 코드가 "1234"인 경우에만 인증 성공)
     *
     * @param email 사용자 이메일
     * @param code 사용자가 입력한 인증 코드
     * @return 인증 코드가 일치하면 true, 불일치하면 false
     */
    public boolean verifyEmailCode(String email, String code) {
        // 이메일 인증 코드 검증
        return code.equals("1234");
    }
}
