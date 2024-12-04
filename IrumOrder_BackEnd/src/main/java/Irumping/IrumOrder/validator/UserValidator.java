package Irumping.IrumOrder.validator;

import Irumping.IrumOrder.controller.SignUpRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 클래스 설명: 사용자 등록 정보를 검증하는 클래스
 * 사용자가 입력한 아이디, 비밀번호, 이메일 정보를 검증
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
public class UserValidator implements Validator {

    /**
     * 검증할 클래스를 지정하는 메소드
     *
     * @param clazz 검증할 클래스
     * @return 검증할 클래스가 SignUpRequest 클래스와 같으면 true, 아니면 false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpRequest.class.equals(clazz);
    }

    /**
     * 사용자 등록 정보를 검증하는 메소드
     *
     * @param target 검증할 객체
     * @param errors 검증 결과 에러를 담는 객체
     */
    @Override
    public void validate(Object target, Errors errors) {    // 검증 로직
        SignUpRequest request = (SignUpRequest) target;

        // id validation
        // 1. id가 null이면 안됨
        // 2. id 길이가 최대 20자 이하
        // 3. id 길이가 최소 4자 이상
        // 4. id는 영어 소문자, 대문자, 숫자만 가능
        if (request.getId() == null) {
            errors.rejectValue("userId", "null", "id가 null입니다.");
        } else if (request.getId().length() > 20) {
            errors.rejectValue("userId", "length", "id 길이가 20자를 초과합니다.");
        } else if (request.getId().length() < 4) {
            errors.rejectValue("userId", "length", "id 길이가 4자 미만입니다.");
        } else if (!request.getId().matches("^[a-zA-Z0-9]*$")) {
            errors.rejectValue("userId", "pattern", "id는 영어 소문자, 대문자, 숫자만 가능합니다.");
        }

        // password validation
        // 1. password가 null이면 안됨
        // 2. password 길이가 최대 20자 이하
        // 3. password 길이가 최소 8자 이상
        // 4. password는 영어 소문자, 대문자, 숫자, 특수문자(!@#$%^&*()-_=+)만 가능
        if (request.getPassword() == null) {
            errors.rejectValue("password", "null", "password가 null입니다.");
        } else if (request.getPassword().length() > 20) {
            errors.rejectValue("password", "length", "password 길이가 20자를 초과합니다.");
        } else if (request.getPassword().length() < 8) {
            errors.rejectValue("password", "length", "password 길이가 4자 미만입니다.");
        } else if (!request.getPassword().matches("^[a-zA-Z0-9!@#$%^&*()-_=+]*$")) {
            errors.rejectValue("password", "pattern", "password는 영어 소문자, 대문자, 숫자, 특수문자(!@#$%^&*()-_=+)만 가능합니다.");
        }

        // email validation
        // 1. email이 null이면 안됨
        // 2. email 형식이어야 함
        if (request.getEmail() == null) {
            errors.rejectValue("email", "null", "email가 null입니다.");
        } else if (!request.getEmail().matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")) {
            errors.rejectValue("email", "pattern", "email 형식이 아닙니다.");
        }
    }
}
