package Irumping.IrumOrder;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import java.io.FileInputStream;

public class FirebaseMessagingTest {

    public static void main(String[] args) {
        try {
            // Firebase 초기화
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream("src/main/resources/firebase/serviceAccount.json")))
                    .build();

            FirebaseApp.initializeApp(options);
            // 하드코딩된 FCM 토큰
            String testToken = "dRsiYYi7B4GRibCcM2LFH7:APA91bEAB_yLfmfkzTVheBk35a44RrNvZaVndBxx9kYryTCRKMj2BHLkdGOSJUPhZkKbwAOTNMGmux2AYrPGxWhSQaDCyh9Ky0gl5WT8-g_Wecly0XDWUSA";

            // Firebase 알림 메시지 생성
            Notification notification = Notification.builder()
                    .setTitle("테스트 제목")
                    .setBody("이것은 백엔드에서 보낸 테스트 메시지입니다.")
                    .build();

            Message message = Message.builder()
                    .setToken(testToken)
                    .setNotification(notification)
                    .build();

            // 메시지 전송
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Firebase 메시지 전송 성공: " + response);

        } catch (Exception e) {
            System.err.println("Firebase 메시지 전송 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
