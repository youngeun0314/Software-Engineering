package Irumping.IrumOrder;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import java.io.FileInputStream;

/**
 * FirebaseMessagingTest
 *
 * 이 클래스는 Firebase Cloud Messaging(FCM)을 통해 테스트 알림 메시지를 전송하는 예제입니다.
 * Firebase 설정과 메시지 전송 로직을 포함하고 있습니다.
 *
 * 작성자: 김은지
 * 작성일: 2024-12-12
 */
public class FirebaseMessagingTest {

    public static void main(String[] args) {
        try {
            // Firebase 초기화
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream("src/main/resources/firebase/serviceAccount.json"))) // Firebase 인증 키 파일 경로
                    .build();

            // FirebaseApp 초기화
            FirebaseApp.initializeApp(options);

            // 하드코딩된 테스트 FCM 토큰
            String testToken = "dRsiYYi7B4GRibCcM2LFH7:APA91bFp8vUY-ND4t0vCPMQOX0ZTQcweoKw2VOZgZTHAPH1tsjdedK6WzkfJqLAgFVo3HpmDVh9BoT2_H9YuVnNsNmRuLBtgrVB_dwczb1jqKiZN3BiYvzs";

            // Firebase 알림 메시지 생성
            Notification notification = Notification.builder()
                    .setTitle("테스트 제목") // 알림 제목
                    .setBody("이것은 백엔드에서 보낸 테스트 메시지입니다.") // 알림 내용
                    .build();

            // 메시지 생성
            Message message = Message.builder()
                    .setToken(testToken) // 대상 디바이스의 FCM 토큰
                    .setNotification(notification) // 알림 메시지
                    .build();

            // 메시지 전송
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Firebase 메시지 전송 성공: " + response);

        } catch (Exception e) {
            // 오류 처리
            System.err.println("Firebase 메시지 전송 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
