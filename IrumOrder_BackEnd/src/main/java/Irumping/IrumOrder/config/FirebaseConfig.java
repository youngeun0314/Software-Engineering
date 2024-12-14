package Irumping.IrumOrder.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        // ClassPath에서 리소스를 로드
        InputStream serviceAccount = new ClassPathResource("firebase/serviceAccount.json").getInputStream();
        log.info("Firebase Service Account Path: {}", new ClassPathResource("firebase/serviceAccount.json").getPath());

        if (!new ClassPathResource("firebase/serviceAccount.json").exists()) {
            throw new IllegalStateException("Firebase serviceAccount.json not found");
        }

        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

        //기존 firebaseApp 인스턴스 확인 및 초기화
        if (firebaseAppList != null && !firebaseAppList.isEmpty()){
            for (FirebaseApp app : firebaseAppList){
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
                    firebaseApp = app;
            }
        }

        //기존 firebaseApp이 없으면 새로초기화
        if (firebaseApp == null) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
            System.out.println("FirebaseApp initialized: " + firebaseApp.getName());
        } else {
            System.out.println("FirebaseApp already initialized: " + firebaseApp.getName());
        }
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
