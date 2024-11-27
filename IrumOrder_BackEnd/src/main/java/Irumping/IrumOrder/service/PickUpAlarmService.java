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
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PickUpAlarmService {

    private UserRepository userRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final TokenRepository tokenRepository;

    public String sendPushNotification(Long userId, String title, String body) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("유저 ID {}를 찾을 수 없습니다.", userId);
                    return new IllegalArgumentException("유저를 찾을 수 없습니다.");
                });

        // 유저의 FCM 토큰 조회
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
            firebaseMessaging.send(message);
            log.info("유저 ID {}에게 알림 전송 성공", userId);
            return "알림을 성공적으로 전송했습니다.";
        } catch (FirebaseMessagingException e) {
            log.error("유저 ID {}에게 알림 전송 실패: {}", userId, e.getMessage(), e);
            return "알림 보내기를 실패하였습니다.";
        }
    }
}
