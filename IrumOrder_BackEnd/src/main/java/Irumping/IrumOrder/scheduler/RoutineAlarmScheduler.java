package Irumping.IrumOrder.scheduler;

import Irumping.IrumOrder.entity.RoutineDay;
import Irumping.IrumOrder.entity.RoutineEntity;
import Irumping.IrumOrder.repository.RoutineRepository;
import Irumping.IrumOrder.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class RoutineAlarmScheduler {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private AlarmService alarmService;

    @Scheduled(cron = "0 0/10 * * * *") // 매 10분마다 실행
    public void checkRoutineAlarms() {
        LocalTime currentTime = LocalTime.now();
        LocalTime twoHoursLater = currentTime.plusHours(2);

        // 오늘의 요일을 RoutineDay로 변환
        RoutineDay today = mapDayOfWeekToRoutineDay(LocalDate.now().getDayOfWeek());

        // 오늘 요일에 활성화된 루틴 가져오기
        List<RoutineEntity> routines = routineRepository.findByRoutineDayAndAlarmEnabled(today, true);

        for (RoutineEntity routine : routines) {
            // 루틴 시간이 2시간 이내인지 확인
            if (routine.getRoutineTime().isAfter(currentTime) && routine.getRoutineTime().isBefore(twoHoursLater)) {
                String title = "Routine Reminder";
                String body = "Time to plan your meal! Reserve your order now.";
                String userToken = getUserFirebaseToken(routine.getUserId());

                if (userToken != null && !userToken.isEmpty()) {
                    alarmService.sendPushNotification(title, body, userToken);
                } else {
                    System.err.println("User token not found for userId: " + routine.getUserId());
                }
            }
        }
    }

    private RoutineDay mapDayOfWeekToRoutineDay(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return RoutineDay.Mon;
            case TUESDAY: return RoutineDay.Tue;
            case WEDNESDAY: return RoutineDay.Wed;
            case THURSDAY: return RoutineDay.Thu;
            case FRIDAY: return RoutineDay.Fri;
            case SATURDAY: return RoutineDay.Sat;
            case SUNDAY: return RoutineDay.Sun;
            default: throw new IllegalArgumentException("Unknown DayOfWeek: " + dayOfWeek);
        }
    }

    private String getUserFirebaseToken(Integer userId) {
        // 사용자 토큰 조회 로직 구현
        // 예: DB나 캐시에서 Firebase 토큰 가져오기
        // 예시: return tokenRepository.findByUserId(userId).getFirebaseToken();
        return "mock_firebase_token"; // 임시 토큰 반환 (DB 로직으로 대체해야 함)
    }
}
