package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.dto.*;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * OrderControllerTest
 *
 * 이 클래스는 OrderController의 주요 기능을 테스트하기 위한 테스트 클래스입니다.
 * 각 테스트는 HTTP 요청 및 응답을 시뮬레이션하여 컨트롤러의 동작을 검증합니다.
 *
 * 작성자: 김은지
 * 작성일: 2024-12-12
 */
class OrderControllerTest {

    @Mock
    private OrderService orderService; // OrderController에서 사용하는 OrderService를 Mock 처리

    @InjectMocks
    private OrderController orderController; // 테스트 대상인 OrderController

    private MockMvc mockMvc; // MockMvc를 통해 HTTP 요청/응답을 시뮬레이션

    private ObjectMapper objectMapper; // JSON 직렬화/역직렬화를 위한 ObjectMapper

    /**
     * 테스트 실행 전 초기화 작업을 수행합니다.
     * MockMvc 및 ObjectMapper를 설정합니다.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito Mock 초기화
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build(); // MockMvc 설정
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules(); // Java 8 DateTime 모듈 등록
    }

    /**
     * Order 생성 테스트
     *
     * 유저 ID와 Order 데이터를 POST 요청으로 전송했을 때, 성공적으로 Order가 생성되고
     * 적절한 응답 데이터가 반환되는지 검증합니다.
     */
    @Test
    void testCreateOrder() throws Exception {
        // Given: 테스트 데이터 준비
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setUserId(1);
        orderRequestDto.setTotalPrice(5000);
        orderRequestDto.setPickUp(LocalTime.of(10, 30));
        orderRequestDto.setOrderMenuOptions(Collections.emptyList());

        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(1);
        responseDto.setUserId(1);
        responseDto.setTotalPrice(5000);

        when(orderService.createOrder(any(OrderRequestDto.class))).thenReturn(responseDto);

        // When & Then: POST 요청을 보내고 응답 상태와 데이터를 검증
        mockMvc.perform(post("/orders/{userId}/order", 1)  // 경로 변수 userId 제공
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isCreated()) // 201 상태 코드 기대
                .andExpect(jsonPath("$.orderId").value(1)) // 응답 필드 검증
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.totalPrice").value(5000));

        verify(orderService, times(1)).createOrder(any(OrderRequestDto.class)); // 서비스 호출 검증
    }

    /**
     * 특정 사용자 ID로 모든 Order 조회 테스트
     *
     * GET 요청으로 특정 사용자의 Order 데이터를 요청했을 때, 올바른 데이터가 반환되는지 검증합니다.
     */
    @Test
    void testGetAllOrdersByUserId() throws Exception {
        // Given: Mock 데이터 준비
        OrdersCheckResponseDto responseDto = new OrdersCheckResponseDto();
        responseDto.setOrderId(1);
        responseDto.setUserId(1);
        responseDto.setTotalPrice(5000);
        responseDto.setOrderStatus(OrderStatus.WAITING);

        when(orderService.getOrdersByUserId(1)).thenReturn(List.of(responseDto));

        // When & Then: 경로 변수 userId를 포함하여 GET 요청을 전송하고 응답 검증
        mockMvc.perform(get("/orders/{userId}/orderCheck", 1).param("userId", "1"))  // 경로 변수 전달
                .andExpect(status().isOk()) // 상태 코드 200 기대
                .andExpect(jsonPath("$[0].orderId").value(1)) // 첫 번째 결과 검증
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].totalPrice").value(5000))
                .andExpect(jsonPath("$[0].orderStatus").value("WAITING"));

        verify(orderService, times(1)).getOrdersByUserId(1); // 서비스 호출 검증
    }
}
