package Irumping.IrumOrder.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * 클래스 설명: 사용자 등록 요청 정보를 담는 클래스
 * 사용자가 입력한 아이디, 비밀번호, 이메일 정보를 담고 있음
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-05
 */
@Getter
@Setter
public class SignUpRequest {

    private String id;
    private String password;
    private String email;

    public SignUpRequest() {
    }

    public SignUpRequest(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }
}
