package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.service.OrderMgmtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * OrderMgmtController 테스트
 *
 * 작성자 : 주영은
 * 마지막 수정일 : 2024-12-09
 */
class OrderMgmtControllerTest {

    @InjectMocks
    private OrderMgmtController orderMgmtController;

    @Mock
    private OrderMgmtService orderMgmtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateOrderStatus_shouldReturnSuccessResponse_whenServiceReturnsSuccess() {
        // Given
        int orderId = 1;
        OrderStatus status = OrderStatus.READY_FOR_PICKUP;

        when(orderMgmtService.updateOrderStatus(orderId, status)).thenReturn("success");

        // When
        ResponseEntity<?> response = orderMgmtController.updateOrderStatus(orderId, status);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("{\"result\":\"success\", \"orderId\":1}", response.getBody());
        verify(orderMgmtService, times(1)).updateOrderStatus(orderId, status);
    }

    @Test
    void updateOrderStatus_shouldReturnFailureResponse_whenServiceReturnsFailure() {
        // Given
        int orderId = 1;
        OrderStatus status = OrderStatus.READY_FOR_PICKUP;

        when(orderMgmtService.updateOrderStatus(orderId, status)).thenReturn("fail: 주문이 존재하지 않습니다.");

        // When
        ResponseEntity<?> response = orderMgmtController.updateOrderStatus(orderId, status);

        // Then
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("{\"result\":\"fail\", \"message\":\"주문이 존재하지 않습니다.\"}", response.getBody());
        verify(orderMgmtService, times(1)).updateOrderStatus(orderId, status);
    }

    @Test
    void checkOrder_shouldReturnOrderEntityResponse_whenOrderExists() {
        // Given
        int orderId = 1;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);

        when(orderMgmtService.checkOrder(orderId)).thenReturn(orderEntity);

        // When
        ResponseEntity<?> response = orderMgmtController.checkOrder(orderId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orderEntity, response.getBody());
        verify(orderMgmtService, times(1)).checkOrder(orderId);
    }

    @Test
    void checkOrder_shouldReturnNotFoundResponse_whenOrderNotExist() {
        // Given
        int orderId = 1;

        when(orderMgmtService.checkOrder(orderId)).thenReturn(null);

        // When
        ResponseEntity<?> response = orderMgmtController.checkOrder(orderId);

        // Then
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("{\"result\":\"fail\", \"message\":\"주문이 존재하지 않습니다.\"}", response.getBody());
        verify(orderMgmtService, times(1)).checkOrder(orderId);
    }
}
