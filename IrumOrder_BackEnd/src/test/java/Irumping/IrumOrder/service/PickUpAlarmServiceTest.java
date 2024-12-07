//package Irumping.IrumOrder.service;
//
//import Irumping.IrumOrder.entity.TokenEntity;
//import Irumping.IrumOrder.entity.UserEntity;
//import Irumping.IrumOrder.repository.TokenRepository;
//import Irumping.IrumOrder.repository.UserRepository;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
///**
// * 테스트 클래스 설명:
// * PickUpAlarmServiceTest는 PickUpAlarmService 클래스의 기능을 단위 테스트하기 위한 클래스입니다.
// * JUnit과 Mockito를 사용하여 Mocking된 레포지토리와 FirebaseMessaging을 테스트합니다.
// */
//@ExtendWith(MockitoExtension.class)
//class PickUpAlarmServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private TokenRepository tokenRepository;
//
//    @Mock
//    private FirebaseMessaging firebaseMessaging;
//
//    @InjectMocks
//    private PickUpAlarmService pickUpAlarmService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testSendPushNotification_UserNotFound() {
//        // Given
//        Integer userId = 1;
//        String title = "Test Notification Title";
//        String body = "Test Notification Body";
//
//        // 유저를 찾을 수 없도록 Mock 설정
//        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());
//
//        // When
//        String result = pickUpAlarmService.sendPushNotification(userId, title, body);
//
//        // Then
//        assertEquals("유저를 찾을 수 없습니다.", result);
//        verify(userRepository, times(1)).findByUserId(userId);
//        verifyNoInteractions(tokenRepository, firebaseMessaging);
//    }
//
//    @Test
//    void testSendPushNotification_Success() throws FirebaseMessagingException {
//        // Given
//        Integer userId = 1;
//        String title = "Test Notification Title";
//        String body = "Test Notification Body";
//
//        // Mock 유저와 토큰 데이터 설정
//        UserEntity mockUser = new UserEntity();
//        mockUser.setUserId(userId);
//
//        TokenEntity mockTokenEntity = new TokenEntity();
//        mockTokenEntity.setFcmToken("valid_token");
//
//        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(mockUser)); // 유저 존재
//        when(tokenRepository.findByUser(mockUser)).thenReturn(mockTokenEntity); // 유효한 FCM 토큰
//        doReturn("mock-message-id").when(firebaseMessaging).send(any(Message.class)); // 메시지 전송 성공 Mock
//
//        // When
//        String result = pickUpAlarmService.sendPushNotification(userId, title, body);
//
//        // Then
//        assertEquals("알림을 성공적으로 전송했습니다.", result);
//        verify(userRepository, times(1)).findByUserId(userId);
//        verify(tokenRepository, times(1)).findByUser(mockUser);
//        verify(firebaseMessaging, times(1)).send(any(Message.class));
//    }
//
//    @Test
//    void testSendPushNotification_TokenNotFound() {
//        // Given
//        Integer userId = 1;
//        String title = "Test Notification Title";
//        String body = "Test Notification Body";
//
//        // Mock 유저 데이터 설정
//        UserEntity mockUser = new UserEntity();
//        mockUser.setUserId(userId);
//
//        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(mockUser)); // 유저 존재
//        when(tokenRepository.findByUser(mockUser)).thenReturn(null); // FCM 토큰이 없는 경우
//
//        // When
//        String result = pickUpAlarmService.sendPushNotification(userId, title, body);
//
//        // Then
//        assertEquals("유저의 FCM 토큰을 찾을 수 없습니다.", result);
//        verify(userRepository, times(1)).findByUserId(userId);
//        verify(tokenRepository, times(1)).findByUser(mockUser);
//        verifyNoInteractions(firebaseMessaging);
//    }
//
//    @Test
//    void testSendPushNotification_FirebaseException() throws FirebaseMessagingException {
//        // Given
//        Long userId = 1L;
//        String title = "Test Notification Title";
//        String body = "Test Notification Body";
//
//        // Mock 유저와 토큰 데이터 설정
//        UserEntity mockUser = new UserEntity();
//        mockUser.setUserId(userId);
//
//        TokenEntity mockTokenEntity = new TokenEntity();
//        mockTokenEntity.setFcmToken("valid_token");
//
//        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(mockUser)); // 유저 존재
//        when(tokenRepository.findByUser(mockUser)).thenReturn(mockTokenEntity); // 유효한 FCM 토큰
//
//        // FirebaseMessaging에서 예외 발생 설정
//        doThrow(new RuntimeException("Firebase error"))
//                .when(firebaseMessaging).send(any(Message.class));
//
//        // When
//        String result = pickUpAlarmService.sendPushNotification(userId, title, body);
//
//        // Then
//        assertEquals("알림 보내기를 실패하였습니다.", result);
//        verify(userRepository, times(1)).findByUserId(userId);
//        verify(tokenRepository, times(1)).findByUser(mockUser);
//        verify(firebaseMessaging, times(1)).send(any(Message.class));
//    }
//}
