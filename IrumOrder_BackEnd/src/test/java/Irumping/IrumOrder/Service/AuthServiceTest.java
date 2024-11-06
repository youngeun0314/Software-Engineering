package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Dto.LoginDto;
import Irumping.IrumOrder.Entity.UserEntity;
import Irumping.IrumOrder.Repository.MockLoginRepository;

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
        UserEntity user = new UserEntity("testId", "testPwd", "testEmail");

        System.out.println(authService);

        // when
        authService.signUp(user);
        // then
        assertThat(authService.login(new LoginDto("testId", "testPwd"))).isTrue();
    }

    @Test
    void login_success() {
        // given
        LoginDto loginDto = new LoginDto("testId", "testPwd");
        authService.signUp(new UserEntity("testId", "testPwd", "testEmail"));

        // when
        boolean check = authService.login(loginDto);

        // then
        assertThat(check).isTrue();
    }

    @Test
    void login_fail_wrong_password() {
        // given
        LoginDto loginDto = new LoginDto("testId", "testPwd");
        authService.signUp(new UserEntity("testId", "testPwd", "testEmail"));

        // when
        boolean check = authService.login(new LoginDto("testId", "wrongPwd"));

        // then
        assertThat(check).isFalse();
    }

//    @Test
//    void login_fail_wrong_id() {
//        // given
//        LoginDto loginDto = new LoginDto("testId", "testPwd");
//        authService.signUp(new UserEntity("testId", "testPwd", "testEmail", "testName"));
//
//        // when
//        boolean check = authService.login(new LoginDto("wrongId", "testPwd"));
//
//        // then
//        assertThat(check).isFalse();
//    }
}
