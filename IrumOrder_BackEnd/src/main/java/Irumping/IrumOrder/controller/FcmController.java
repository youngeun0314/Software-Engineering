package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.entity.UserEntity;
import Irumping.IrumOrder.service.FcmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 클래스 설명: FCM 관련 API를 제공하는 컨트롤러
 * 사용자 FCM 토큰 등록 기능을 제공
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-05
 */
@RestController
@RequestMapping("/api/fcm")
public class FcmController {

    private final FcmService fcmService;

    /**
     * 생성자를 통한 FcmService 의존성 주입
     *
     * @param fcmService FcmService 객체
     */
    public FcmController(FcmService fcmService) {
        this.fcmService = fcmService;
    }

    /**
     * FCM 토큰 등록 메소드
     *
     * @param tokenRequest 사용자 FCM 토큰 요청 정보 (id, fcmToken)
     * @return FCM 토큰 등록 성공 메시지
     */
    @PostMapping("/register-token")
    public ResponseEntity<String> registerToken(@RequestBody TokenRequest tokenRequest) {

        UserEntity user = fcmService.findUser(tokenRequest.getId());

        fcmService.saveToken(user, tokenRequest.getFcmToken());
        return ResponseEntity.ok("Token registered successfully");
    }
}
