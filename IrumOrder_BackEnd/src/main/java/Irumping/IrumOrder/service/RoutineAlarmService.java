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

/**
 * 클래스 설명: RoutineAlarmService는 루틴 알람을 처리하는 서비스 클래스입니다.
 *             사용자의 루틴 정보를 확인하고, 알림 조건에 맞는 경우 푸시 알림을 전송합니다.
 *
 * 주요 기능:
 * - 오늘 날짜와 시간을 기준으로 활성화된 루틴을 조회.
 * - 루틴 알림 시간(현재 시간 기준 2시간 전후)에 해당하는 경우 알림 전송.
 * - Firebase Cloud Messaging(FCM)을 통해 사용자에게 알림 전송.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoutineAlarmService {

    private final RoutineRepository routineRepository;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final FirebaseMessaging firebaseMessaging;

    /**
     * 루틴 알람을 처리하는 메인 메서드입니다.
     * 현재 시간 기준 2시간 전후의 알림 조건에 맞는 루틴을 조회하고, 푸시 알림을 전송합니다.
     */
    public void processRoutineAlarms() {
        LocalTime currentTime = LocalTime.now();
        LocalTime twoHoursLater = currentTime.plusHours(2);

        log.info("현재 시간: {}", currentTime);
        log.info("2시간 후 시간: {}", twoHoursLater);

        // 오늘 요일 매핑
        RoutineDay today = mapDayOfWeekToRoutineDay(LocalDate.now().getDayOfWeek());
        log.info("오늘 요일: {}", today);

        // 모든 루틴을 가져와 필터링
        List<RoutineEntity> allRoutines = routineRepository.findAll();
        List<RoutineEntity> filteredRoutines = allRoutines.stream()
                .filter(routine -> routine.getAlarmEnabled()) // 알람 활성화 여부 확인
                .filter(routine -> RoutineDayUtils.isSpecificDay(routine.getRoutineDayBitmask(), today)) // 오늘 요일에 해당하는 루틴 필터링
                .toList();

        log.info("조건에 부합하는 루틴 수: {}", filteredRoutines.size());
        filteredRoutines.forEach(routine -> log.info("루틴 ID: {}, 요일 비트마스크: {}, 시간: {}, 활성화 여부: {}",
                routine.getRoutineId(), routine.getRoutineDayBitmask(), routine.getRoutineTime(), routine.getAlarmEnabled()));

        for (RoutineEntity routine : filteredRoutines) {
            log.info("루틴 ID: {}, 루틴 시간: {}", routine.getRoutineId(), routine.getRoutineTime());
            if (routine.getRoutineTime().isAfter(twoHoursLater.minusMinutes(5)) &&
                    routine.getRoutineTime().isBefore(twoHoursLater.plusMinutes(5))) {
                log.info("루틴 ID {}가 조건에 부합하여 알림을 전송합니다.", routine.getRoutineId());

                try {
                    // 사용자 확인
                    UserEntity user = userRepository.findByUserId(routine.getUserId())
                            .orElseThrow(() -> {
                                log.warn("User ID {}를 찾을 수 없습니다.", routine.getUserId());
                                return new IllegalArgumentException("유효하지 않은 사용자 ID");
                            });

                    log.info("User ID {} 확인 완료. 사용자 이름: {}", user.getUserId(), user.getId());

                    // 토큰 확인
                    TokenEntity tokenEntity = tokenRepository.findByUser(user);
                    if (tokenEntity == null || tokenEntity.getFcmToken() == null || tokenEntity.getFcmToken().isEmpty()) {
                        log.warn("User ID {}의 유효한 FCM 토큰이 없습니다.", routine.getUserId());
                        continue;
                    }

                    log.info("User ID {}의 FCM 토큰: {}", user.getUserId(), tokenEntity.getFcmToken());

                    // 알림 전송
                    sendPushNotification(
                            routine.getUserId(),
                            tokenEntity.getFcmToken(),
                            "픽업 예약 알림",
                            "2시간 뒤 픽업 주문을 지금 예약하세요!"
                    );
                } catch (Exception e) {
                    log.error("루틴 ID {} 알림 전송 실패: {}", routine.getRoutineId(), e.getMessage(), e);
                }
            } else {
                log.info("루틴 ID {}는 시간 조건을 만족하지 않습니다. 루틴 시간: {}, 비교 시간: {}",
                        routine.getRoutineId(), routine.getRoutineTime(), twoHoursLater);
            }
        }
        log.info("Routine 알람 처리 완료");
    }

    /**
     * FCM을 사용해 푸시 알림을 전송하는 메서드입니다.
     *
     * @param userId   알림을 받을 사용자 ID
     * @param fcmToken FCM 토큰
     * @param title    알림 제목
     * @param body     알림 내용
     */
    void sendPushNotification(Integer userId, String fcmToken, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(fcmToken)
                .putData("click_action", "OPEN_CART") //click했을때 액션(만약안되면이부분지우기)
                .build();

        int retryCount = 3; // 최대 재시도 횟수

        for (int i = 0; i < retryCount; i++) {
            try {
                log.info("User ID {}에게 알림 전송 시도 ({}회). 제목: {}, 내용: {}", userId, i + 1, title, body);
                String response = firebaseMessaging.send(message);
                log.info("User ID {}에게 알림 전송 성공. Firebase 응답: {}", userId, response);
                break; // 성공하면 루프 종료
            } catch (FirebaseMessagingException e) {
                log.error("User ID {}에게 알림 전송 실패. 재시도: {}/{}", userId, i + 1, retryCount, e);

                if (i == retryCount - 1) {
                    // 재시도 횟수를 초과한 경우 예외를 던짐
                    throw new RuntimeException("알림 전송에 실패했습니다. User ID: " + userId, e);
                }

                try {
                    // 재시도 전 잠시 대기
                    Thread.sleep(2000); // 2초 대기
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    log.warn("재시도 대기 중 인터럽트 발생", interruptedException);
                }
            }
        }
    }


    /**
     * Java의 DayOfWeek를 RoutineDay로 매핑하는 메서드입니다.
     *
     * @param dayOfWeek 오늘 날짜의 DayOfWeek 객체
     * @return RoutineDay에 해당하는 값
     */
    private RoutineDay mapDayOfWeekToRoutineDay(DayOfWeek dayOfWeek) {
        log.debug("DayOfWeek 매핑: {}", dayOfWeek);
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
