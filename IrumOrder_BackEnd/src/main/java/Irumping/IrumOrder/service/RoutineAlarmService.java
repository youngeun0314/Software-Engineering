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
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoutineAlarmService {

    private final RoutineRepository routineRepository;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final FirebaseMessaging firebaseMessaging;

    public void processRoutineAlarms() {
        LocalTime currentTime = LocalTime.now();
        LocalTime twoHoursLater = currentTime.plusHours(2);

        // 오늘 요일 매핑
        RoutineDay today = mapDayOfWeekToRoutineDay(LocalDate.now().getDayOfWeek());

        // 조건에 맞는 루틴 조회
        List<RoutineEntity> routines = routineRepository.findByRoutineDayAndAlarmEnabledTrue(today);

        for (RoutineEntity routine : routines) {
            if (routine.getRoutineTime().equals(twoHoursLater)) {
                log.info("루틴 ID {}가 조건에 부합하여 알림을 전송합니다.", routine.getRoutineId());

                try {
                    UserEntity user = userRepository.findById(routine.getUserId())
                            .orElseThrow(() -> {
                                log.warn("User ID {}를 찾을 수 없습니다.", routine.getUserId());
                                return new IllegalArgumentException("유효하지 않은 사용자 ID");
                            });

                    TokenEntity tokenEntity = tokenRepository.findByUser(user);
                    if (tokenEntity == null || tokenEntity.getFcmToken() == null || tokenEntity.getFcmToken().isEmpty()) {
                        log.warn("User ID {}의 유효한 FCM 토큰이 없습니다.", routine.getUserId());
                        continue;
                    }

                    sendPushNotification(
                            routine.getUserId(),
                            tokenEntity.getFcmToken(),  // FCM 토큰 전달
                            "픽업 예약 알림",
                            "2시간 뒤 픽업 주문을 지금 예약하세요!"
                    );
                } catch (Exception e) {
                    log.error("루틴 ID {} 알림 전송 실패: {}", routine.getRoutineId(), e.getMessage(), e);
                }
            }
        }
        log.info("Routine 알람 처리 완료");
    }

    private void sendPushNotification(Long userId, String fcmToken, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(fcmToken)  // 정확한 FCM 토큰 설정
                .build();

        try {
            firebaseMessaging.send(message);
            log.info("User ID {}에게 알림 전송 성공", userId);
        } catch (FirebaseMessagingException e) {
            log.error("User ID {}에게 알림 전송 실패: {}", userId, e.getMessage(), e);
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
}
