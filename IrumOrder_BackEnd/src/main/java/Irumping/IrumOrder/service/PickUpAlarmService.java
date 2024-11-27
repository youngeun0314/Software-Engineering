package Irumping.IrumOrder.service;

import Irumping.IrumOrder.repository.TokenRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PickUpAlarmService {

    private FirebaseMessaging firebaseMessaging;

    @Autowired
    private TokenRepository tokenRepository;

    public String sendPushNotification(Integer userId, String title, String body) {
        // 유저토큰조회
        String userToken = tokenRepository.findByUserId(userId).getFcmToken();

        if (userToken == null || userToken.isEmpty()){
            return "유저의 FCM 토큰을 찾을 수 없습니다.";
        }
        Notification notification = Notification.builder()
                .setTitle(title).setBody(body).build();
        Message message = Message.builder()
                .setToken(userToken).setNotification(notification).build();

        try {
            firebaseMessaging.send(message);
            return "알림을 성공적으로 전송했습니다.";
        } catch(FirebaseMessagingException e){
            e.printStackTrace();
            return "알림 보내기를 실패하였습니다.";
        }

    }
}
