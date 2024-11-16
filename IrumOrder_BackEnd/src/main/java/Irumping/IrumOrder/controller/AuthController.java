package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.service.AuthService;
import Irumping.IrumOrder.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @InitBinder // validator 설정
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new UserValidator());
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@Validated SignUpRequest signUpRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("회원가입 실패");
            bindingResult.getAllErrors().forEach(error -> log.info(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        try {
            authService.signUp(signUpRequest.getUserId(), signUpRequest.getPassword(), signUpRequest.getEmail());
            log.info("{}님 회원가입 완료", signUpRequest.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
        } catch (IllegalArgumentException e) {
            log.info("회원가입 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(String userId, String password) {
        if (authService.login(userId, password)) {
            log.info("{}님 로그인 성공", userId);
            return ResponseEntity.ok("로그인 성공");
        } else {
            log.info("로그인 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }
}
