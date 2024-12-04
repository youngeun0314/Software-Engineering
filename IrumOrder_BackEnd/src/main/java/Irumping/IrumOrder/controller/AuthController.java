package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.service.AuthService;
import Irumping.IrumOrder.validator.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * 클래스 설명: 사용자 인증 관련 API를 제공하는 컨트롤러
 * 사용자 등록, 로그인, 이메일 인증 등의 기능을 제공
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */

//@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Validator 설정하는 메소드
     *
     * @param dataBinder WebDataBinder 객체
     */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new UserValidator());
    }

    @Operation(
            summary = "아이디 중복 체크",
            description = "사용 가능한 아이디인지 확인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용 가능한 아이디입니다."),
                    @ApiResponse(responseCode = "400", description = "아이디가 이미 존재합니다.")
            }
    )

    /**
     * 사용자 등록 시, 사용자가 입력한 아이디가 이미 존재하는지 확인하는 메소드
     *
     * @param id 사용자가 입력한 아이디
     * @return 사용 가능한 아이디인 경우 200 OK, 이미 존재하는 아이디인 경우 400 BAD REQUEST
     */
    @PostMapping("/checkId")
    public ResponseEntity<String> checkId(@Parameter(description = "확인할 아이디", example = "user123") @RequestParam String id) {
        if (authService.isExist(id)) {
            log.info("id is exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디가 이미 존재합니다.");
        } else {
            log.info("id is not exist");
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        }
    }

    /**
     * 사용자 등록 API
     *
     * @param signUpRequest 사용자 등록 요청 정보(아이디, 비밀번호, 이메일)
     * @param bindingResult 유효성 검사
     * @return 회원가입 완료 시 201 CREATED, 실패 시 400 BAD REQUEST
     */
    @Operation(
            summary = "회원가입",
            description = "새로운 사용자를 등록합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "회원가입 완료"),
                    @ApiResponse(responseCode = "400", description = "회원가입 실패")
            }
    )
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(
            @Parameter(description = "회원가입 요청 정보") @Validated SignUpRequest signUpRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("회원가입 실패");
            bindingResult.getAllErrors().forEach(error -> log.info(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        try {
            authService.signUp(signUpRequest.getId(), signUpRequest.getPassword(), signUpRequest.getEmail());
            log.info("{}님 회원가입 완료", signUpRequest.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
        } catch (IllegalArgumentException e) {
            log.info("회원가입 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }


    /**
     * 사용자 등록 시, 이메일 인증 코드 전송 메소드
     * (인증 코드는 "1234"로 고정해둠)
     *
     * @param email 사용자가 입력한 이메일 주소
     * @return 인증 번호가 이메일로 전송되었습니다.
     */
    @Operation(
            summary = "이메일 인증 코드 전송",
            description = "사용자의 이메일로 인증 코드를 전송합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증 번호가 이메일로 전송되었습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @PostMapping("/sendEmailVerification")
    public ResponseEntity<String> sendEmailVerification(
            @Parameter(description = "인증 코드를 받을 이메일 주소", example = "user@example.com")
            @RequestParam String email) {
        String code = authService.generateVerificationCode();
        authService.sendVerificationEmail(email, code);
        return ResponseEntity.ok("인증 번호가 이메일로 전송되었습니다.");
    }

    /**
     * 사용자 등록 시, 이메일 인증 메소드
     * (인증 코드가 "1234"인 경우에만 인증 성공)
     *
     * @param email 사용자가 입력한 이메일 주소
     * @param code 사용자가 입력한 인증 코드
     * @return 이메일 인증이 완료되었습니다.
     */
    @Operation(
            summary = "이메일 인증",
            description = "사용자가 받은 인증 코드를 검증합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이메일 인증이 완료되었습니다."),
                    @ApiResponse(responseCode = "400", description = "인증 번호가 올바르지 않습니다.")
            }
    )
    @PostMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(
            @Parameter(description = "인증할 이메일 주소", example = "user@example.com")
            @RequestParam String email,
            @Parameter(description = "사용자가 받은 인증 코드", example = "1234")
            @RequestParam String code) {
        if (authService.verifyEmailCode(email, code)) {
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 번호가 올바르지 않습니다.");
        }
    }

    /**
     * 사용자 로그인 메소드
     *
     * @param id 사용자가 입력한 아이디
     * @param password 사용자가 입력한 비밀번호
     * @return 로그인 성공 시 200 OK, 실패 시 401 UNAUTHORIZED
     */
    @Operation(
            summary = "로그인",
            description = "사용자의 아이디와 비밀번호로 로그인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "401", description = "로그인 실패")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "사용자의 아이디", example = "user123")
            @RequestParam String id,
            @Parameter(description = "사용자의 비밀번호", example = "password")
            @RequestParam String password) {
        if (authService.login(id, password)) {
            log.info("{}님 로그인 성공", id);
            return ResponseEntity.ok("로그인 성공");
        } else {
            log.info("로그인 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }
}
