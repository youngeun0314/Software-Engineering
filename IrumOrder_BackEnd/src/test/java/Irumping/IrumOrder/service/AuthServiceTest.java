package Irumping.IrumOrder.service;

import Irumping.IrumOrder.repository.MockLoginRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AuthServiceTest {

    private static AuthService authService;


    @BeforeEach
    public void beforeEach() {
        authService = new AuthService(new MockLoginRepository());
    }

    @AfterEach
    public void afterEach() {
        authService = null;
    }

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
