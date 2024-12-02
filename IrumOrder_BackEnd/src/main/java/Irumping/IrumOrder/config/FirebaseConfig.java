package Irumping.IrumOrder.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
<<<<<<< HEAD
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
=======
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
>>>>>>> 7f7bb4bb8ba8586161cc4e94df55a95add8ad11c

@Configuration
public class FirebaseConfig {

<<<<<<< HEAD
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        // ClassPath에서 리소스를 로드
        InputStream serviceAccount = new ClassPathResource("firebase/serviceAccount.json").getInputStream();

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
        if (firebaseApp == null ){
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }

        return FirebaseMessaging.getInstance(firebaseApp);
=======
    @PostConstruct
    public void initializeFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("path/to/firebase/service-account.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
>>>>>>> 7f7bb4bb8ba8586161cc4e94df55a95add8ad11c
    }
}
