package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.controller.TokenRequest;
import Irumping.IrumOrder.entity.UserEntity;
import Irumping.IrumOrder.service.FcmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * FcmController 테스트
 *
 * 이 클래스는 FcmController의 주요 기능을 테스트하기 위한 테스트 클래스입니다.
 * 각 테스트는 HTTP 요청 및 응답을 시뮬레이션하여 컨트롤러의 동작을 검증합니다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-12
 */
class FcmControllerTest {

    @InjectMocks
    private FcmController fcmController;

    @Mock
    private FcmService fcmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 정상적인 FCM 토큰 등록을 테스트합니다.
     * 사용자가 존재하고 유효한 토큰이 제공되었을 때 "Token registered successfully" 메시지가 반환되어야 합니다.
     */
    @Test
    void registerToken_shouldReturnSuccessResponse_whenTokenRegisteredSuccessfully() {
        // Given
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setId("testUser");
        tokenRequest.setFcmToken("testToken123");

        UserEntity userEntity = new UserEntity();
        userEntity.setId("testUser");

        when(fcmService.findUser("testUser")).thenReturn(userEntity);

        // When
        ResponseEntity<?> response = fcmController.registerToken(tokenRequest);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Token registered successfully", response.getBody());
        verify(fcmService, times(1)).findUser("testUser");
        verify(fcmService, times(1)).saveToken(userEntity, "testToken123");
    }

    /**
     * 사용자를 찾을 수 없는 경우를 테스트합니다.
     * 존재하지 않는 사용자를 조회할 때 "사용자를 찾을 수 없습니다" 메시지가 반환되어야 합니다.
     */
    @Test
    void registerToken_shouldReturnNotFoundResponse_whenUserNotFound() {
        // Given
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setId("nonExistingUser");
        tokenRequest.setFcmToken("testToken123");

        when(fcmService.findUser("nonExistingUser")).thenReturn(null);

        // When
        ResponseEntity<?> response = fcmController.registerToken(tokenRequest);

        // Then
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("{\"result\":\"fail\", \"message\":\"사용자를 찾을 수 없습니다.\"}", response.getBody());
        verify(fcmService, times(1)).findUser("nonExistingUser");
        verify(fcmService, never()).saveToken(any(), any());
    }

    /**
     * 유효하지 않은 FCM 토큰을 처리하는 테스트입니다.
     * 빈 문자열 토큰이 제공될 때 "유효하지 않은 FCM 토큰입니다" 메시지가 반환되어야 합니다.
     */
    @Test
    void registerToken_shouldReturnBadRequestResponse_whenTokenIsInvalid() {
        // Given
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setId("testUser");
        tokenRequest.setFcmToken(""); // 빈 토큰

        UserEntity userEntity = new UserEntity();
        userEntity.setId("testUser");

        when(fcmService.findUser("testUser")).thenReturn(userEntity);

        // When
        ResponseEntity<?> response = fcmController.registerToken(tokenRequest);

        // Then
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("{\"result\":\"fail\", \"message\":\"유효하지 않은 FCM 토큰입니다.\"}", response.getBody());
        verify(fcmService, times(1)).findUser("testUser");
        verify(fcmService, never()).saveToken(any(), any());
    }
}
