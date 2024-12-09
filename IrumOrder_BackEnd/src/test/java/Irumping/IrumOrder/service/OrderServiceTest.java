package Irumping.IrumOrder.service;

import Irumping.IrumOrder.dto.*;
import Irumping.IrumOrder.entity.*;
import Irumping.IrumOrder.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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
     * 메서드 설명: 주문 생성 테스트
     *
     * 주문 요청 데이터를 사용하여 올바른 OrderEntity가 저장되고
     * 응답 데이터가 올바른지 확인하는 테스트.
     */
    @Test
    void createOrder_shouldSaveOrderAndReturnResponse() {
        // Given: OrderRequestDto 생성
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setUserId(1);
        orderRequestDto.setTotalPrice(10000);
        orderRequestDto.setPickUp(LocalTime.of(10, 0)); // LocalTime 사용

        // OrderMenuDto 및 MenuDetailDto 객체 생성 및 초기화
        MenuDetailDto menuDetailDto = new MenuDetailDto();
        menuDetailDto.setUseCup("TakeOut");
        menuDetailDto.setAddShot(true);
        menuDetailDto.setAddVanilla(true);
        menuDetailDto.setAddHazelnut(false);
        menuDetailDto.setLight(false);

        OrderMenuDto orderMenuDto = new OrderMenuDto();
        orderMenuDto.setMenuId(1);
        orderMenuDto.setQuantity(2);
        orderMenuDto.setMenuOptions(menuDetailDto);

        orderRequestDto.setOrderMenuOptions(Collections.singletonList(orderMenuDto));

        // Mocking: CategoryEntity 및 MenuEntity 생성
        CategoryEntity mockCategory = new CategoryEntity();
        mockCategory.setId(1);
        mockCategory.setName("Beverage");

        MenuEntity mockMenu = new MenuEntity("Americano", 5000, mockCategory);
        mockMenu.setMenuId(1);
        when(menuRepository.findMenuById(1)).thenReturn(mockMenu);

        // Mocking: MenuDetailEntity 저장
        doAnswer(invocation -> {
            MenuDetailEntity savedEntity = invocation.getArgument(0);
            assertNotNull(savedEntity);
            return null;
        }).when(menuDetailRepository).save(any(MenuDetailEntity.class));

        // Mocking: OrderEntity 저장
        doAnswer(invocation -> {
            OrderEntity savedOrder = invocation.getArgument(0);
            savedOrder.setOrderId(1);
            savedOrder.setPickUp(LocalTime.of(10, 0));
            return null;
        }).when(orderRepository).save(any(OrderEntity.class));

        // When: createOrder 호출
        OrderResponseDto result = orderService.createOrder(orderRequestDto);

        // Then: 결과 검증
        assertNotNull(result);
        assertEquals(1, result.getOrderId());
        assertEquals(10000, result.getTotalPrice());
        assertEquals(OrderStatus.WAITING, result.getOrderStatus());
        assertEquals(LocalTime.of(10, 0), result.getPickUp());

        // Verify: 저장 메서드 호출 검증
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(orderMenuRepository, times(1)).saveAll(anyList());
        verify(menuRepository, times(1)).findMenuById(1);
        verify(menuDetailRepository, times(1)).save(any(MenuDetailEntity.class));
    }

    /**
     * 메서드 설명: 특정 사용자 ID로 주문 조회 테스트
     *
     * 사용자 ID를 기반으로 올바른 주문 목록이 반환되는지 확인.
     */
    @Test
    void getOrdersByUserId_shouldReturnOrderList() {
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

        OrderEntity mockOrder = new OrderEntity();
        mockOrder.setOrderId(1);
        mockOrder.setUserId(1);
        mockOrder.setTotalPrice(10000);
        mockOrder.setOrderStatus(OrderStatus.WAITING);
        mockOrder.setPickUp(LocalTime.of(10, 0));
        mockOrder.setOrderMenuOptions(Collections.singletonList(mockOrderMenu));

        when(orderRepository.findByUserId(1)).thenReturn(Collections.singletonList(mockOrder));

        // When: getOrdersByUserId 호출
        List<OrdersCheckResponseDto> result = orderService.getOrdersByUserId(1);

        // Then: 결과 검증
        assertNotNull(result);
        assertEquals(1, result.size());
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
