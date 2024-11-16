package Irumping.IrumOrder.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private static AuthService authService;

    @DisplayName("Test SignUp")
    @Test
    void signUp() {
        // given
        // when
        authService.signUp("testId", "testPwd", "testEmail");
        // then
        assertThat(authService.login("testId", "testPwd")).isTrue();
    }

    @Test
    void loginSuccess() {
        // given
        authService.signUp("testId", "testPwd", "testEmail");

        // when
        boolean check = authService.login("testId", "testPwd");

        // then
        assertThat(check).isTrue();
    }

    @Test
    void loginFailNoId() {
        // given

        // when
        boolean check = authService.login("testId", "wrongPwd");

        // then
        assertThat(check).isFalse();
    }

    @Test
    void loginFailWrongPwd() {
        // given
        authService.signUp("testId", "testPwd", "testEmail");

        // when
        boolean check = authService.login("testId", "wrongPwd");

        // then
        assertThat(check).isFalse();
    }
}
