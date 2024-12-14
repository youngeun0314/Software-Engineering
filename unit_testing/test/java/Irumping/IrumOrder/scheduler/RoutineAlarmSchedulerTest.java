package Irumping.IrumOrder.scheduler;

import Irumping.IrumOrder.service.RoutineAlarmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

/**
 * 클래스 설명: RoutineAlarmScheduler 테스트 클래스
 *
 * 스케줄러가 올바르게 RoutineAlarmService를 호출하는지 테스트.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-09
 */
class RoutineAlarmSchedulerTest {

    @Mock
    private RoutineAlarmService routineAlarmService;

    @InjectMocks
    private RoutineAlarmScheduler routineAlarmScheduler;

    /**
     * 메서드 설명: 테스트 초기화 메서드
     *
     * Mockito를 사용해 목 객체 초기화.
     */
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
        // When: 스케줄러 메서드 실행
        routineAlarmScheduler.checkRoutineAlarms();

        // Then: RoutineAlarmService의 메서드 호출 검증
        verify(routineAlarmService, times(1)).processRoutineAlarms();
    }

    /**
     * 테스트 설명: `checkRoutineAlarms` 실행 중 예외가 발생하더라도
     * 서비스가 중단되지 않고 로그만 출력되는지 확인.
     */
    @Test
    void testCheckRoutineAlarms_ExceptionHandling() {
        // Given: RoutineAlarmService에서 예외 발생 설정
        doThrow(new RuntimeException("Routine 처리 중 오류"))
                .when(routineAlarmService).processRoutineAlarms();

        // When: 스케줄러 메서드 실행
        routineAlarmScheduler.checkRoutineAlarms();

        // Then: 예외 발생에도 메서드가 호출되었는지 검증
        verify(routineAlarmService, times(1)).processRoutineAlarms();
    }
}
