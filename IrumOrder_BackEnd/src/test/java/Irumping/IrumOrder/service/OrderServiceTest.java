package Irumping.IrumOrder.service;

import Irumping.IrumOrder.dto.*;
import Irumping.IrumOrder.entity.*;
import Irumping.IrumOrder.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 클래스 설명: OrderService 테스트 클래스
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-09
 */
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private JpaMenuRepository menuRepository;

    @Mock
    private MenuDetailRepository menuDetailRepository;

    @Mock
    private OrderMenuRepository orderMenuRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 메서드 설명: 특정 사용자 ID로 주문 조회 테스트
     *
     * 사용자 ID를 기반으로 payment가 null이 아닌 올바른 주문 목록이 반환되는지 확인.
     */
    @Test
    void getOrdersByUserId_shouldReturnOnlyOrdersWithPayment() {
        // Given: Mock 데이터 설정
        CategoryEntity mockCategory = new CategoryEntity();
        mockCategory.setId(1);
        mockCategory.setName("Beverage");

        MenuEntity mockMenu = new MenuEntity("Americano", 5000, mockCategory);
        mockMenu.setMenuId(1);

        MenuDetailEntity mockMenuDetail = new MenuDetailEntity();
        mockMenuDetail.setUseCup("TakeOut");
        mockMenuDetail.setAddShot(false);
        mockMenuDetail.setAddVanilla(true);
        mockMenuDetail.setAddHazelnut(true);
        mockMenuDetail.setLight(false);

        OrderMenuEntity mockOrderMenu = new OrderMenuEntity();
        mockOrderMenu.setMenu(mockMenu);
        mockOrderMenu.setMenuDetail(mockMenuDetail);
        mockOrderMenu.setQuantity(2);

        // 첫 번째 주문 (payment != null)
        OrderEntity mockOrderWithPayment = new OrderEntity();
        mockOrderWithPayment.setOrderId(1);
        mockOrderWithPayment.setUserId(1);
        mockOrderWithPayment.setTotalPrice(10000);
        mockOrderWithPayment.setOrderStatus(OrderStatus.WAITING);
        mockOrderWithPayment.setPickUp(LocalTime.of(10, 0));
        mockOrderWithPayment.setPayment(LocalDateTime.now());
        mockOrderWithPayment.setOrderMenuOptions(Collections.singletonList(mockOrderMenu));

        // 두 번째 주문 (payment == null)
        OrderEntity mockOrderWithoutPayment = new OrderEntity();
        mockOrderWithoutPayment.setOrderId(2);
        mockOrderWithoutPayment.setUserId(1);
        mockOrderWithoutPayment.setTotalPrice(15000);
        mockOrderWithoutPayment.setOrderStatus(OrderStatus.WAITING);
        mockOrderWithoutPayment.setPickUp(LocalTime.of(11, 0));
        mockOrderWithoutPayment.setPayment(null);
        mockOrderWithoutPayment.setOrderMenuOptions(Collections.singletonList(mockOrderMenu));

        // Mocking: findByUserId에서 두 주문 반환
        when(orderRepository.findByUserId(1))
                .thenReturn(List.of(mockOrderWithPayment, mockOrderWithoutPayment));

        // When: getOrdersByUserId 호출
        List<OrdersCheckResponseDto> result = orderService.getOrdersByUserId(1);

        // Then: 결과 검증
        assertNotNull(result);
        assertEquals(1, result.size()); // payment가 null이 아닌 주문만 포함
        OrdersCheckResponseDto response = result.get(0);
        assertEquals(1, response.getOrderId());
        assertEquals(10000, response.getTotalPrice());
        assertEquals(OrderStatus.WAITING, response.getOrderStatus());
        assertEquals(LocalTime.of(10, 0), response.getPickUp());
        assertEquals(1, response.getOrderMenuOptions().size());

        OrderDto orderMenuDto = response.getOrderMenuOptions().get(0);
        assertEquals(1, orderMenuDto.getMenuId());
        assertEquals("Americano", orderMenuDto.getMenuName());
        assertEquals(2, orderMenuDto.getQuantity());

        // Verify: findByUserId 호출 검증
        verify(orderRepository, times(1)).findByUserId(1);
    }
}
