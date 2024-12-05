package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.TokenEntity;
import Irumping.IrumOrder.entity.UserEntity;
import Irumping.IrumOrder.repository.TokenRepository;
import Irumping.IrumOrder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 클래스 설명: FCM 관련 기능을 제공하는 서비스
 * 사용자 FCM 토큰 저장 및 사용자 조회 기능을 포함
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-05
 */
@RequiredArgsConstructor
@Service
public class FcmService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    /**
     * 사용자 ID를 기반으로 사용자 정보를 조회
     *
     * @param id 사용자 아이디
     * @return UserEntity 사용자 정보
     * @throws IllegalArgumentException 사용자 정보가 존재하지 않을 경우 예외 발생
     */
    public UserEntity findUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    /**
     * 사용자 FCM 토큰을 저장
     *
     * @param user 사용자 정보
     * @param fcmToken FCM 토큰
     */
    @Transactional
    public void saveToken(UserEntity user, String fcmToken) {
        TokenEntity tokenEntity = new TokenEntity(user, fcmToken);
        tokenRepository.save(tokenEntity);
    }
}
