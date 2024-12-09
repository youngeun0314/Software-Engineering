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

class PickUpAlarmServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FirebaseMessaging firebaseMessaging;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private PickUpAlarmService pickUpAlarmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendPushNotification_Success() throws FirebaseMessagingException {
        // given
        Integer userId = 1;
        String title = "픽업 알림";
        String body = "주문하신 상품이 준비되었습니다.";
        String fcmToken = "test_token";

        UserEntity user = new UserEntity();
        user.setUserId(userId);

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setFcmToken(fcmToken);

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(tokenRepository.findByUser(user)).thenReturn(tokenEntity);
        when(firebaseMessaging.send(any())).thenReturn("success");

        // when
        String result = pickUpAlarmService.sendPushNotification(userId, title, body);

        // then
        assertEquals("알림을 성공적으로 전송했습니다.", result);
        verify(userRepository).findByUserId(userId);
        verify(tokenRepository).findByUser(user);
        verify(firebaseMessaging).send(any());
    }

}
