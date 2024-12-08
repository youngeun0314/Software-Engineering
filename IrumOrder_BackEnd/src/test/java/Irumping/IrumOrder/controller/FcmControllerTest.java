package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.controller.TokenRequest;
import Irumping.IrumOrder.entity.UserEntity;
import Irumping.IrumOrder.service.FcmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FcmControllerTest {

    @Mock
    private FcmService fcmService;

    @InjectMocks
    private FcmController fcmController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fcmController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testRegisterToken() throws Exception {
        // Given
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setId("testUser");
        tokenRequest.setFcmToken("testToken123");

        UserEntity userEntity = new UserEntity();
        userEntity.setId("testUser");

        when(fcmService.findUser("testUser")).thenReturn(userEntity);

        // When & Then
        mockMvc.perform(post("/api/fcm/register-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Token registered successfully"));

        verify(fcmService, times(1)).findUser("testUser");
        verify(fcmService, times(1)).saveToken(userEntity, "testToken123");
    }
}
