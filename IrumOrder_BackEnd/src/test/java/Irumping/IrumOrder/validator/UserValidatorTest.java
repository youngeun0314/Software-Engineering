package Irumping.IrumOrder.validator;

import Irumping.IrumOrder.controller.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.assertj.core.api.Assertions.assertThat;

class UserValidatorTest {

    private final UserValidator validator = new UserValidator();

    @ParameterizedTest
    @DisplayName("ID 유효성 검사 테스트")
    @CsvSource(value = {
            "null; password123; user@example.com; id; null; id가 null입니다.",
            "tooLongUsername1234567890; password123; user@example.com; id; length; id 길이가 20자를 초과합니다.",
            "usr; password123; user@example.com; id; length; id 길이가 4자 미만입니다.",
            "invalid*id; password123; user@example.com; id; pattern; id는 영어 소문자, 대문자, 숫자만 가능합니다."
    }, delimiter = ';')
    void testIdValidation(String id, String password, String email, String field, String errorCode, String defaultMessage) {
        SignUpRequest request = new SignUpRequest(id.equals("null") ? null : id, password, email);
        Errors errors = new BeanPropertyBindingResult(request, "signUpRequest");

        validator.validate(request, errors);

        assertThat(errors.hasFieldErrors(field)).isTrue();
        assertThat(errors.getFieldError(field).getCode()).isEqualTo(errorCode);
        assertThat(errors.getFieldError(field).getDefaultMessage()).isEqualTo(defaultMessage);
    }

    @ParameterizedTest
    @DisplayName("Password 유효성 검사 테스트")
    @CsvSource(value = {
            "user123; null; user@example.com; password; null; password가 null입니다.",
            "user123; tooLongPassword1234567890; user@example.com; password; length; password 길이가 20자를 초과합니다.",
            "user123; short; user@example.com; password; length; password 길이가 8자 미만입니다.",
            "user123; invalid|password; user@example.com; password; pattern; password는 영어 소문자, 대문자, 숫자, 특수문자(!@#$%^&*()-_=+)만 가능합니다."
    }, delimiter = ';')
    void testPasswordValidation(String id, String password, String email, String field, String errorCode, String defaultMessage) {
        SignUpRequest request = new SignUpRequest(id, password.equals("null") ? null : password, email);
        Errors errors = new BeanPropertyBindingResult(request, "signUpRequest");

        validator.validate(request, errors);

        assertThat(errors.hasFieldErrors(field)).isTrue();
        assertThat(errors.getFieldError(field).getCode()).isEqualTo(errorCode);
        assertThat(errors.getFieldError(field).getDefaultMessage()).isEqualTo(defaultMessage);
    }

    @ParameterizedTest
    @DisplayName("Email 유효성 검사 테스트")
    @CsvSource(value = {
            "user123; password123; null; email; null; email가 null입니다.",
            "user123; password123; invalid-email; email; pattern; email 형식이 아닙니다."
    }, delimiter = ';')
    void testEmailValidation(String id, String password, String email, String field, String errorCode, String defaultMessage) {
        SignUpRequest request = new SignUpRequest(id, password, email.equals("null") ? null : email);
        Errors errors = new BeanPropertyBindingResult(request, "signUpRequest");

        validator.validate(request, errors);

        assertThat(errors.hasFieldErrors(field)).isTrue();
        assertThat(errors.getFieldError(field).getCode()).isEqualTo(errorCode);
        assertThat(errors.getFieldError(field).getDefaultMessage()).isEqualTo(defaultMessage);
    }
}
