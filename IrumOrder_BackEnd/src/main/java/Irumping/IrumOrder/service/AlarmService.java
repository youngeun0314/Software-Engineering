package Irumping.IrumOrder.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {

    public void sendPushNotification(String title, String body, String userToken) {
        try {
            Message message = Message.builder()
                    .putData("title", title)
                    .putData("body", body)
                    .setToken(userToken)
                    .build();

            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
