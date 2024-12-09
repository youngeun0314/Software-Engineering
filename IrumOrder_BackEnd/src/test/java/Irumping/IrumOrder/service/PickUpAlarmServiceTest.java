package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.TokenEntity;
import Irumping.IrumOrder.entity.UserEntity;
import Irumping.IrumOrder.repository.TokenRepository;
import Irumping.IrumOrder.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * 클래스 설명: 픽업 알림 서비스 테스트 클래스
 *
 * 사용자에게 Firebase 알림을 전송하는 로직에 대한 단위 테스트를 포함.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-09
 */
class PickUpAlarmServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FirebaseMessaging firebaseMessaging;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private PickUpAlarmService pickUpAlarmService;

    /**
     * 메서드 설명: 테스트 초기화 메서드
     *
     * Mockito를 사용해 목 객체 초기화.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 메서드 설명: 성공적인 푸시 알림 전송 테스트
     *
     * 주어진 사용자 ID와 제목, 내용을 기반으로 Firebase를 통해
     * 성공적으로 알림이 전송되는지 검증.
     *
     * @throws FirebaseMessagingException Firebase 메시징 예외
     */
    @Test
    void testSendPushNotification_Success() throws FirebaseMessagingException {
        // Given: 테스트 데이터 준비
        Integer userId = 1;
        String title = "픽업 알림";
        String body = "주문하신 상품이 준비되었습니다.";
        String fcmToken = "test_token";

        UserEntity user = new UserEntity();
        user.setUserId(userId);

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setFcmToken(fcmToken);

        // Mocking: Repository 및 Firebase 호출 설정
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(tokenRepository.findByUser(user)).thenReturn(tokenEntity);
        when(firebaseMessaging.send(any())).thenReturn("success");

        // When: 알림 전송 호출
        String result = pickUpAlarmService.sendPushNotification(userId, title, body);

        // Then: 결과 검증
        assertEquals("알림을 성공적으로 전송했습니다.", result);
        verify(userRepository).findByUserId(userId); // 사용자 조회 확인
        verify(tokenRepository).findByUser(user);   // FCM 토큰 조회 확인
        verify(firebaseMessaging).send(any());     // Firebase 알림 전송 확인
    }
}
