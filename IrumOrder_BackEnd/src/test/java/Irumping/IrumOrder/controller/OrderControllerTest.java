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

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules(); // Java 8 DateTime 모듈 등록
    }

    @Test
    void testCreateOrder() throws Exception {
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

        mockMvc.perform(post("/orders/{userId}/order", 1)  // 경로 변수 userId 제공
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isCreated()) // 201 상태 코드 기대
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.totalPrice").value(5000));

        verify(orderService, times(1)).createOrder(any(OrderRequestDto.class));
    }

    @Test
    void testGetAllOrdersByUserId() throws Exception {
        // Given: Mock 데이터 준비
        OrdersCheckResponseDto responseDto = new OrdersCheckResponseDto();
        responseDto.setOrderId(1);
        responseDto.setUserId(1);
        responseDto.setTotalPrice(5000);
        responseDto.setOrderStatus(OrderStatus.WAITING);

        when(orderService.getOrdersByUserId(1)).thenReturn(List.of(responseDto));

        // When & Then: 경로 변수 userId를 포함하여 요청
        mockMvc.perform(get("/orders/{userId}/orderCheck", 1).param("userId", "1"))  // 경로 변수 전달
                .andExpect(status().isOk()) // 상태 코드 200 기대
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].totalPrice").value(5000))
                .andExpect(jsonPath("$[0].orderStatus").value("WAITING"));

        verify(orderService, times(1)).getOrdersByUserId(1); // 서비스 호출 확인
    }

}
