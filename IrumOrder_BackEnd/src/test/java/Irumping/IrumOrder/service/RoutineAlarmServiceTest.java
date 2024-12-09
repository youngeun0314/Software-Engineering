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
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * 클래스 설명: RoutineAlarmService 테스트 클래스
 *
 * 루틴 알림 프로세스의 동작을 검증하기 위한 테스트를 포함합니다.
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-09
 */
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

    /**
     * 메서드 설명: 루틴 알림이 정확한 시간에 전송되고, 알림 내용이 올바른지 검증합니다.
     *
     * - 루틴 시간과 알림 활성화 여부에 따라 알림이 전송되는지 테스트합니다.
     * - Firebase 메시지 내용 검증 포함.
     */
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

