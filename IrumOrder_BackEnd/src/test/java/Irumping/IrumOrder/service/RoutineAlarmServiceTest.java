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
    void testProcessRoutineAlarms_SendsNotificationAtRightTime_WithMockBuilder_UsingToString() throws FirebaseMessagingException {
        // Arrange
        LocalTime fixedTime = LocalTime.of(10, 0);
        RoutineEntity routine = new RoutineEntity();
        routine.setRoutineId(1);
        routine.setUserId(123);

        // 월요일 비트마스크 생성
        int sundayBitmask = RoutineDayUtils.toBitmask(Collections.singletonList(RoutineDay.Mon));
        routine.setRoutineDayBitmask(sundayBitmask);
        routine.setRoutineTime(fixedTime.plusHours(2)); // 12:00
        routine.setAlarmEnabled(true);

        TokenEntity token = new TokenEntity();
        token.setFcmToken("mock-fcm-token");

        UserEntity user = new UserEntity();
        user.setUserId(123);
        user.setId("testUser");

        when(routineRepository.findAll()).thenReturn(Collections.singletonList(routine));
        when(userRepository.findByUserId(123)).thenReturn(Optional.of(user));
        when(tokenRepository.findByUser(user)).thenReturn(token);

        // Mock Message.builder() 설정
        com.google.firebase.messaging.Message.Builder messageBuilderMock = mock(com.google.firebase.messaging.Message.Builder.class);
        com.google.firebase.messaging.Message messageMock = mock(com.google.firebase.messaging.Message.class);

        when(messageBuilderMock.setNotification(any())).thenReturn(messageBuilderMock);
        when(messageBuilderMock.setToken(anyString())).thenReturn(messageBuilderMock);
        when(messageBuilderMock.putData(anyString(), anyString())).thenReturn(messageBuilderMock);
        when(messageBuilderMock.build()).thenReturn(messageMock);

        when(messageMock.toString()).thenReturn(
                "Message{token='mock-fcm-token', notification=Notification{title='픽업 예약 알림', body='2시간 뒤 픽업 주문을 지금 예약하세요!'}, data={click_action=OPEN_CART}}"
        );

        // Act
        routineAlarmService.processRoutineAlarms();

        // Assert
        String sentMessageString = messageMock.toString();

        // toString을 활용한 검증
        assertTrue(sentMessageString.contains("mock-fcm-token"), "토큰 정보가 포함되어야 합니다.");
        assertTrue(sentMessageString.contains("픽업 예약 알림"), "알림 제목이 포함되어야 합니다.");
        assertTrue(sentMessageString.contains("2시간 뒤 픽업 주문을 지금 예약하세요!"), "알림 내용이 포함되어야 합니다.");
        assertTrue(sentMessageString.contains("click_action=OPEN_CART"), "데이터 필드 'click_action'이 포함되어야 합니다.");
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
        LocalTime fixedTime = LocalTime.of(21, 35); // 테스트에서 고정된 현재 시간
        RoutineEntity routine = new RoutineEntity();
        routine.setRoutineId(1);
        routine.setUserId(123);

        // 현재 요일(SUNDAY)에 맞는 비트마스크 생성
        int sundayBitmask = RoutineDayUtils.toBitmask(Collections.singletonList(RoutineDay.Sun));
        routine.setRoutineDayBitmask(sundayBitmask);

        // 루틴 시간 설정 (현재 시간의 2시간 후로 고정)
        routine.setRoutineTime(fixedTime.plusHours(2)); // 23:35
        routine.setAlarmEnabled(true);

        TokenEntity token = new TokenEntity();
        token.setFcmToken("mock-fcm-token");

        UserEntity user = new UserEntity();
        user.setUserId(123);

        when(routineRepository.findAll()).thenReturn(Collections.singletonList(routine));
        when(userRepository.findByUserId(123)).thenReturn(Optional.of(user));
        when(tokenRepository.findByUser(user)).thenReturn(token);

        // FirebaseMessaging.send()에 대한 Mock 설정
        doAnswer(invocation -> {
            // 첫 번째 호출에서 예외를 던짐
            throw new RuntimeException("Simulated network failure");
        }).doReturn("mock-response") // 두 번째 호출에서 정상 응답 반환
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

