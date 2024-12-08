package Irumping.IrumOrder.scheduler;

import Irumping.IrumOrder.service.RoutineAlarmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class RoutineAlarmSchedulerTest {

    @Mock
    private RoutineAlarmService routineAlarmService;

    @InjectMocks
    private RoutineAlarmScheduler routineAlarmScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mock 초기화
    }

    /**
     * 테스트 설명: `checkRoutineAlarms` 메서드가 호출되었을 때
     * `RoutineAlarmService.processRoutineAlarms`가 실행되는지 확인.
     */
    @Test
    void testCheckRoutineAlarms_Success() {

        routineAlarmScheduler.checkRoutineAlarms();

        verify(routineAlarmService, times(1)).processRoutineAlarms();
    }

    /**
     * 테스트 설명: `checkRoutineAlarms` 실행 중 예외가 발생하더라도
     * 서비스가 중단되지 않고 로그만 출력되는지 확인.
     */
    @Test
    void testCheckRoutineAlarms_ExceptionHandling() {

        doThrow(new RuntimeException("Routine 처리 중 오류")).when(routineAlarmService).processRoutineAlarms();

        routineAlarmScheduler.checkRoutineAlarms();

        verify(routineAlarmService, times(1)).processRoutineAlarms(); // 예외 발생에도 호출됨
    }
}
