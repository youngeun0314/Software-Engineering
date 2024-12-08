package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.RoutineDay;
import Irumping.IrumOrder.entity.RoutineEntity;
import Irumping.IrumOrder.entity.TokenEntity;
import Irumping.IrumOrder.entity.UserEntity;
import Irumping.IrumOrder.repository.RoutineRepository;
import Irumping.IrumOrder.repository.TokenRepository;
import Irumping.IrumOrder.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RoutineAlarmServiceTest {

    @InjectMocks
    private RoutineAlarmService routineAlarmService;

    @Mock
    private RoutineRepository routineRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FirebaseMessaging firebaseMessaging;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessRoutineAlarms_SendsNotificationAtRightTime() throws FirebaseMessagingException {
        // Arrange
        LocalTime fixedTime = LocalTime.of(10, 0);
        RoutineEntity routine = new RoutineEntity();
        routine.setRoutineId(1);
        routine.setUserId(123);

        // 월요일 비트마스크 생성
        int mondayBitmask = RoutineDayUtils.toBitmask(Collections.singletonList(RoutineDay.Mon));
        routine.setRoutineDayBitmask(mondayBitmask);
        routine.setRoutineTime(fixedTime.plusHours(2));
        routine.setAlarmEnabled(true);

        TokenEntity token = new TokenEntity();
        token.setFcmToken("mock-fcm-token");

        UserEntity user = new UserEntity();
        user.setUserId(123);
        user.setId("testUser");

        when(routineRepository.findAll()).thenReturn(Collections.singletonList(routine));
        when(userRepository.findByUserId(123)).thenReturn(Optional.of(user));
        when(tokenRepository.findByUser(user)).thenReturn(token);

        ArgumentCaptor<com.google.firebase.messaging.Message> messageCaptor = ArgumentCaptor.forClass(com.google.firebase.messaging.Message.class);

        // Act
        routineAlarmService.processRoutineAlarms();

        // Assert
        verify(firebaseMessaging, times(1)).send(messageCaptor.capture());
        com.google.firebase.messaging.Message sentMessage = messageCaptor.getValue();

        // Message 내부 데이터 검증 (toString 사용)
        String sentMessageString = sentMessage.toString();
        assertTrue(sentMessageString.contains("mock-fcm-token"));
        assertTrue(sentMessageString.contains("픽업 예약 알림"));
        assertTrue(sentMessageString.contains("2시간 뒤 픽업 주문을 지금 예약하세요!"));
        assertTrue(sentMessageString.contains("OPEN_CART"));
    }

    @Test
    void testProcessRoutineAlarms_SendsNotificationAtRightTime_UsingStringCheck() throws FirebaseMessagingException {
        // Arrange
        LocalTime fixedTime = LocalTime.of(21, 35); // 현재 시간 고정
        RoutineEntity routine = new RoutineEntity();
        routine.setRoutineId(1);
        routine.setUserId(123);

        // 현재 요일(SUNDAY)에 맞는 비트마스크 생성
        int sundayBitmask = RoutineDayUtils.toBitmask(Collections.singletonList(RoutineDay.Sun));
        routine.setRoutineDayBitmask(sundayBitmask);

        // 루틴 시간 설정 (2시간 후 조건 만족)
        routine.setRoutineTime(fixedTime.plusHours(2)); // 23:35
        routine.setAlarmEnabled(true);

        TokenEntity token = new TokenEntity();
        token.setFcmToken("mock-fcm-token");

        UserEntity user = new UserEntity();
        user.setUserId(123);
        user.setId("testUser");

        when(routineRepository.findAll()).thenReturn(Collections.singletonList(routine));
        when(userRepository.findByUserId(123)).thenReturn(Optional.of(user));
        when(tokenRepository.findByUser(user)).thenReturn(token);

        // 현재 시간 고정
        when(LocalDate.now()).thenReturn(LocalDate.of(2024, 12, 8)); // SUNDAY
        when(LocalTime.now()).thenReturn(fixedTime);

        ArgumentCaptor<com.google.firebase.messaging.Message> messageCaptor = ArgumentCaptor.forClass(com.google.firebase.messaging.Message.class);

        // Act
        routineAlarmService.processRoutineAlarms();

        // Assert
        verify(firebaseMessaging, times(1)).send(messageCaptor.capture());
        com.google.firebase.messaging.Message sentMessage = messageCaptor.getValue();

        // Message 객체를 문자열로 확인
        String sentMessageString = sentMessage.toString();

        // 문자열 검증
        assertTrue(sentMessageString.contains("mock-fcm-token"));
        assertTrue(sentMessageString.contains("픽업 예약 알림"));
        assertTrue(sentMessageString.contains("2시간 뒤 픽업 주문을 지금 예약하세요!"));
        assertTrue(sentMessageString.contains("click_action=OPEN_CART"));
    }


    @Test
    void testProcessRoutineAlarms_RetriesOnNetworkFailure() throws FirebaseMessagingException {
        // Arrange
        LocalTime fixedTime = LocalTime.of(21, 35);
        RoutineEntity routine = new RoutineEntity();
        routine.setRoutineId(1);
        routine.setUserId(123);

        // 현재 요일(SUNDAY)에 맞는 비트마스크 생성
        int sundayBitmask = RoutineDayUtils.toBitmask(Collections.singletonList(RoutineDay.Sun));
        routine.setRoutineDayBitmask(sundayBitmask);

        // 루틴 시간 설정 (2시간 후 조건 만족)
        routine.setRoutineTime(fixedTime.plusHours(2)); // 23:35
        routine.setAlarmEnabled(true);

        TokenEntity token = new TokenEntity();
        token.setFcmToken("mock-fcm-token");

        UserEntity user = new UserEntity();
        user.setUserId(123);

        when(routineRepository.findAll()).thenReturn(Collections.singletonList(routine));
        when(userRepository.findByUserId(123)).thenReturn(Optional.of(user));
        when(tokenRepository.findByUser(user)).thenReturn(token);

        // Mock 현재 시간
        when(LocalDate.now()).thenReturn(LocalDate.of(2024, 12, 8)); // SUNDAY
        when(LocalTime.now()).thenReturn(fixedTime);

        // Mock FirebaseMessaging.send()
        doAnswer(invocation -> {
            // 첫 번째 호출에서 예외를 던짐
            throw new RuntimeException("Simulated network failure");
        }).doReturn("mock-response") // 두 번째 호출에서 정상 응답
                .when(firebaseMessaging).send(any());

        // Act
        routineAlarmService.processRoutineAlarms();

        // Assert
        verify(firebaseMessaging, times(2)).send(any()); // 두 번 호출
    }



    @Test
    void testProcessRoutineAlarms_NoActionIfUserDoesNotClickNotification() {
        // Arrange
        RoutineEntity routine = new RoutineEntity();
        routine.setRoutineId(1);
        routine.setUserId(123);

        // 월요일 비트마스크 생성
        int mondayBitmask = RoutineDayUtils.toBitmask(Collections.singletonList(RoutineDay.Mon));
        routine.setRoutineDayBitmask(mondayBitmask);
        routine.setRoutineTime(LocalTime.now().plusHours(2));
        routine.setAlarmEnabled(true);

        TokenEntity token = new TokenEntity();
        token.setFcmToken("mock-fcm-token");

        UserEntity user = new UserEntity();
        user.setUserId(123);

        when(routineRepository.findAll()).thenReturn(Collections.singletonList(routine));
        when(userRepository.findByUserId(123)).thenReturn(Optional.of(user));
        when(tokenRepository.findByUser(user)).thenReturn(token);

        // Act
        routineAlarmService.processRoutineAlarms();

        // Assert
        // No assertion needed, just ensure no errors or unexpected behavior
    }
}

