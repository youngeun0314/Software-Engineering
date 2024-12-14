package Irumping.IrumOrder.scheduler;

import Irumping.IrumOrder.service.RoutineAlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 클래스 설명: RoutineAlarmScheduler는 정해진 주기에 따라 루틴 알람을 처리하는 스케줄러 클래스입니다.
 *             Spring의 @Scheduled 어노테이션을 사용하여 주기적으로 RoutineAlarmService를 호출합니다.
 *
 * 주요 기능:
 *  - 매 10분마다 루틴 알람을 처리하는 메서드 실행.
 *  - RoutineAlarmService의 processRoutineAlarms() 호출.
 *  - 예외 발생 시 오류 로그 기록.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RoutineAlarmScheduler {

    private final RoutineAlarmService routineAlarmService;

    /**
     * 매 10분마다 루틴 알람을 확인하고 처리하는 메서드입니다.
     * Spring의 @Scheduled(cron) 어노테이션을 사용하여 10분 주기로 실행됩니다.
     *
     * 주요 동작:
     * - 루틴 알람 처리 시작 로그 기록.
     * - RoutineAlarmService의 processRoutineAlarms() 호출.
     * - 처리 중 예외 발생 시 오류 로그 기록.
     * - 처리 완료 후 종료 로그 기록.
     */
    @Scheduled(cron = "0 0/10 * * * *")
    public void checkRoutineAlarms() {
        log.info("Routine 알람 스케줄러 시작");

        try {
            routineAlarmService.processRoutineAlarms();
        } catch (Exception e) {
            log.error("Routine 알람 처리 중 오류 발생: {}", e.getMessage(), e);
        }

        log.info("Routine 알람 스케줄러 종료");
    }
}
