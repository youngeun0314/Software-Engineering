package Irumping.IrumOrder.scheduler;

import Irumping.IrumOrder.service.RoutineAlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoutineAlarmScheduler {

    private final RoutineAlarmService routineAlarmService;

    // 매 10분마다 실행
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
