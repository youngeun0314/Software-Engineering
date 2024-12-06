package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.TokenEntity;
import Irumping.IrumOrder.entity.UserEntity;
import Irumping.IrumOrder.repository.TokenRepository;
import Irumping.IrumOrder.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 클래스 설명: PickUpAlarmService는 특정 사용자에게 Firebase Cloud Messaging(FCM)을 통해
 *             푸시 알림을 전송하는 기능을 제공합니다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-06
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PickUpAlarmService {

    private final UserRepository userRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final TokenRepository tokenRepository;

    /**
     * 특정 사용자에게 푸시 알림을 전송합니다.
     *
     * @param userId 알림을 받을 사용자의 ID
     * @param title  알림 제목
     * @param body   알림 내용
     * @return 알림 전송 성공 또는 실패 메시지
     */
    public String sendPushNotification(Long userId, String title, String body) {

        // 사용자 정보 조회
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("유저 ID {}를 찾을 수 없습니다.", userId);
                    return new IllegalArgumentException("유저를 찾을 수 없습니다.");
                });

        // 사용자의 FCM 토큰 조회
        TokenEntity tokenEntity = tokenRepository.findByUser(user);

        if (tokenEntity == null || tokenEntity.getFcmToken() == null || tokenEntity.getFcmToken().isEmpty()) {
            log.warn("유저 ID {}의 FCM 토큰을 찾을 수 없습니다.", userId);
            return "유저의 FCM 토큰을 찾을 수 없습니다.";
        }

        String userToken = tokenEntity.getFcmToken();

        // Firebase Notification 생성
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        // Firebase Message 생성
        Message message = Message.builder()
                .setToken(userToken)
                .setNotification(notification)
                .build();

        try {
            // FCM을 통해 메시지 전송
            String response = firebaseMessaging.send(message);
            log.info("유저 ID {}에게 알림 전송 성공. Firebase 응답: {}", userId, response);
            return "알림을 성공적으로 전송했습니다.";
        } catch (FirebaseMessagingException e) {
            log.error("FirebaseMessagingException 발생: {}", e.getLocalizedMessage());
            log.error("유저 ID {}에게 알림 전송 실패. 오류 메시지: {}", userId, e.getMessage());
            e.printStackTrace(); // 전체 예외 스택 트레이스 출력 (디버깅용)
            return "알림 보내기를 실패하였습니다.";
        }
    }

}
