package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.event.PickUpAlarmEvent;
import Irumping.IrumOrder.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * OrderMgmtService 테스트
 *
 * 작성자 : 주영은
 * 마지막 수정일 : 2024-12-09
 */
class OrderMgmtServiceTest {

    @InjectMocks
    private OrderMgmtService orderMgmtService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateOrderStatus_shouldUpdateStatusAndPublishEvent_whenStatusIsReadyForPickup() {
        // Given
        Integer orderId = 1;
        OrderStatus newStatus = OrderStatus.READY_FOR_PICKUP;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);
        orderEntity.setOrderStatus(OrderStatus.NEW_ORDER);

        when(orderRepository.findByOrderId(orderId)).thenReturn(orderEntity);

        // When
        String result = orderMgmtService.updateOrderStatus(orderId, newStatus);

        // Then
        assertEquals("success", result);
        assertEquals(OrderStatus.READY_FOR_PICKUP, orderEntity.getOrderStatus());
        verify(orderRepository, times(1)).save(orderEntity);
        verify(eventPublisher, times(1)).publishEvent(any(PickUpAlarmEvent.class));
    }

    @Test
    void updateOrderStatus_shouldReturnFailure_whenOrderNotFound() {
        // Given
        Integer orderId = 1;
        OrderStatus newStatus = OrderStatus.READY_FOR_PICKUP;

        when(orderRepository.findByOrderId(orderId)).thenReturn(null);

        // When
        String result = orderMgmtService.updateOrderStatus(orderId, newStatus);

        // Then
        assertEquals("fail: 주문이 존재하지 않습니다.", result);
        verify(orderRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void checkOrder_shouldReturnOrderEntity_whenOrderExists() {
        // Given
        Integer orderId = 1;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);
        orderEntity.setOrderStatus(OrderStatus.WAITING);

        when(orderRepository.findByOrderId(orderId)).thenReturn(orderEntity);

        // When
        OrderEntity result = orderMgmtService.checkOrder(orderId);

        // Then
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        verify(orderRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void checkOrder_shouldReturnNull_whenOrderDoesNotExist() {
        // Given
        Integer orderId = 1;

        when(orderRepository.findByOrderId(orderId)).thenReturn(null);

        // When
        OrderEntity result = orderMgmtService.checkOrder(orderId);

        // Then
        assertNull(result);
        verify(orderRepository, times(1)).findByOrderId(orderId);
    }
}
