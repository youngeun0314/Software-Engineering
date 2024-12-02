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
import java.util.stream.Collectors;

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

        // 오늘의 요일을 RoutineDay로 변환
        RoutineDay today = mapDayOfWeekToRoutineDay(LocalDate.now().getDayOfWeek());

        // 전체 데이터베이스에서 오늘 요일과 알람 활성화 조건에 맞는 루틴 조회
        List<RoutineEntity> routines = routineRepository.findByRoutineDayAndAlarmEnabledTrue(today);

        for (RoutineEntity routine : routines) {
            // 루틴 시간이 정확히 2시간 뒤인지 확인
            if (routine.getRoutineTime().equals(twoHoursLater)) {
                log.info("루틴 ID {}가 조건에 부합하여 알림을 전송합니다.", routine.getRoutineId());

                try {
                    // userId로 UserEntity 조회
                    UserEntity user = userRepository.findById(routine.getUserId())
                            .orElseThrow(() -> {
                                log.warn("User ID {}를 데이터베이스에서 찾을 수 없습니다.", routine.getUserId());
                                return new IllegalArgumentException("유효하지 않은 사용자 ID");
                            });

                    // 사용자 Firebase 토큰 조회
                    TokenEntity tokenEntity = tokenRepository.findByUser(user);
                    if (tokenEntity == null || tokenEntity.getFcmToken() == null || tokenEntity.getFcmToken().isEmpty()) {
                        log.warn("User ID {}는 유효한 FCM 토큰이 없습니다.", routine.getUserId());
                        continue;
                    }

                    // 알림 전송
                    sendPushNotification(
                            routine.getUserId(),
                            "픽업 예약 알림",
                            "2시간 뒤 픽업 주문을 지금 예약하세요!"
                    );
                    log.info("루틴 ID {} (User ID {}) 알림 전송 성공", routine.getRoutineId(), routine.getUserId());

                } catch (Exception e) {
                    log.error("루틴 ID {} (User ID {}) 알림 전송 실패: {}", routine.getRoutineId(), routine.getUserId(), e.getMessage(), e);
                }
            }
        }
        log.info("Routine 알람 처리 종료");
    }

    private void sendPushNotification(Long userId, String title, String body) {
        // Firebase Notification 생성
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        // Firebase Message 생성
        Message message = Message.builder()
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            log.info("유저 ID {}에게 알림 전송 성공", userId);
        } catch (FirebaseMessagingException e) {
            log.error("유저 ID {}에게 알림 전송 실패: {}", userId, e.getMessage(), e);
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

