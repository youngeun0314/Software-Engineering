package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.dto.LoginDto;
import Irumping.IrumOrder.entity.UserEntity;
import Irumping.IrumOrder.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    public void signUp(String id, String password, String email, String name) {
        UserEntity user = new UserEntity(id, password, email, name);
        authService.signUp(user);
        log.info("{}님 회원가입 완료", name);
    }

    public void login(String id, String password) {
        LoginDto loginDto = new LoginDto(id, password);
        if (authService.login(loginDto)) {
            log.info("{}님 로그인 성공", id);
        } else {
            log.info("로그인 실패");
        }
    }
}
