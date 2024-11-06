package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Service.AuthService;
import Irumping.IrumOrder.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new UserValidator());
    }

    @PostMapping("/signUp")
    public String signUp(@Validated SignUpRequest signUpRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // 로그 변경 해야 함.
            log.info("회원가입 실패");
            bindingResult.getAllErrors().forEach(error -> log.info(error.getDefaultMessage()));
            return ""; // api sepc에 따라 응답 필요.
        }

        authService.signUp(signUpRequest.getId(), signUpRequest.getPassword(), signUpRequest.getEmail());
        log.info("{}님 회원가입 완료", signUpRequest.getId());
        return ""; // api sepc에 따라 응답 필요.
    }

    @PostMapping("/login")
    public String login(String id, String password) {
        if (authService.login(id, password)) {
            log.info("{}님 로그인 성공", id);
            return ""; // api sepc에 따라 응답 필요.
        } else {
            log.info("로그인 실패");
            return ""; // api sepc에 따라 응답 필요.
        }
    }
}
