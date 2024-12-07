package Irumping.IrumOrder.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * 클래스 설명: 사용자 토큰 요청 정보를 담는 클래스
 * 사용자가 전송한 FCM 토큰과 아이디 정보를 담고 있음
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-05
 */
@Getter
@Setter
public class TokenRequest {
    private String fcmToken;
    private String id; // 사용자 ID 추가

}
