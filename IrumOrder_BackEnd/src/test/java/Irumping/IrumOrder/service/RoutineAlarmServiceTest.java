//package Irumping.IrumOrder.service;
//
//import Irumping.IrumOrder.entity.RoutineDay;
//import Irumping.IrumOrder.entity.RoutineEntity;
//import Irumping.IrumOrder.entity.TokenEntity;
//import Irumping.IrumOrder.entity.UserEntity;
//import Irumping.IrumOrder.repository.RoutineRepository;
//import Irumping.IrumOrder.repository.TokenRepository;
//import Irumping.IrumOrder.repository.UserRepository;
//import Irumping.IrumOrder.service.RoutineAlarmService;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//
//class RoutineAlarmServiceTest {
//
//    @Mock
//    private RoutineRepository routineRepository;
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
//    private RoutineAlarmService routineAlarmService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void processRoutineAlarms_shouldSendNotificationForMatchingRoutines() throws FirebaseMessagingException {
//        // Arrange
//        RoutineEntity routine = new RoutineEntity();
//        routine.setRoutineId(1);
//        routine.setUserId(1L);
//        routine.setRoutineTime(LocalTime.now().plusHours(2)); // 정확히 2시간 뒤로 설정
//        routine.setRoutineDay(RoutineDay.Mon);
//        routine.setAlarmEnabled(true);
//
//        UserEntity user = new UserEntity();
//        user.setUserId(1L);
//
//        TokenEntity token = new TokenEntity();
//        token.setFcmToken("testToken");
//
//        when(routineRepository.findByRoutineDayAndAlarmEnabledTrue(RoutineDay.Mon))
//                .thenReturn(List.of(routine));
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(tokenRepository.findByUser(user)).thenReturn(token);
//
//        // Act
//        routineAlarmService.processRoutineAlarms();
//
//        // Assert
//        verify(firebaseMessaging, times(1)).send(any());
//    }
//
//
//    @Test
//    void processRoutineAlarms_shouldSkipIfNoMatchingRoutines() throws FirebaseMessagingException {
//        // Arrange
//        when(routineRepository.findByRoutineDayAndAlarmEnabledTrue(RoutineDay.Mon))
//                .thenReturn(List.of()); // 비어있는 루틴 리스트를 반환
//
//        // Act
//        routineAlarmService.processRoutineAlarms();
//
//        // Assert
//        verify(firebaseMessaging, never()).send(any()); // send()가 호출되지 않았는지 확인
//    }
//
//}
