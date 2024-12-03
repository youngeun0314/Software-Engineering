
/*
package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Dto.OrderRequestDto;
import Irumping.IrumOrder.Dto.OrderMenuDto;
import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.OrderMenuEntity;
import Irumping.IrumOrder.Entity.MenuEntity;
import Irumping.IrumOrder.Repository.OrderRepository;
import Irumping.IrumOrder.Repository.OrderMenuRepository;
import Irumping.IrumOrder.Repository.MenuRepository;
import Irumping.IrumOrder.Repository.MenuDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMenuRepository orderMenuRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuDetailRepository menuDetailRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // 주문 생성 테스트
    @Test
    public void testCreateOrder() {
        // given
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setUserId(1);
        orderRequestDto.setTotalPrice(10000);

        OrderMenuDto orderMenuDto = new OrderMenuDto();
        orderMenuDto.setMenuId(1);
        orderMenuDto.setMenuDetailId(1);
        orderMenuDto.setQuantity(2);

        orderRequestDto.setOrderMenuOptions(List.of(orderMenuDto));

        MenuEntity menu = new MenuEntity("Coffee", 5000, "Beverage");
        when(menuRepository.findById(1)).thenReturn(Optional.of(menu));

        OrderEntity savedOrder = new OrderEntity();
        savedOrder.setOrderId(1);
        savedOrder.setUserId(orderRequestDto.getUserId());
        savedOrder.setTotalPrice(orderRequestDto.getTotalPrice());
        savedOrder.setOrderMenuOptions(List.of(new OrderMenuEntity())); // Ensure orderMenuOptions is not null

        // Mock the save method to return a non-null OrderEntity
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedOrder);

        // when
        OrderEntity result = orderService.createOrder(orderRequestDto);

        // then
        assertNotNull(result);  // Verify that result is not null
        assertEquals(1, result.getOrderId());
        assertEquals(10000, result.getTotalPrice());
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }
}
*/