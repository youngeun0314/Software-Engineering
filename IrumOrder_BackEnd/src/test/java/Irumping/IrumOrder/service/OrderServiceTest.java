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

    @Test
    void createOrder_shouldSaveOrderAndReturnResponse() {
        // Given: OrderRequestDto 생성
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setUserId(1);
        orderRequestDto.setTotalPrice(10000);
        orderRequestDto.setPickUp(LocalTime.of(10, 0)); // LocalTime 사용

        // MenuDetailDto 객체 생성 및 초기화
        MenuDetailDto menuDetailDto = new MenuDetailDto();
        menuDetailDto.setUseCup("TakeOut");
        menuDetailDto.setAddShot(true);
        menuDetailDto.setAddVanilla(true);
        menuDetailDto.setAddHazelnut(false);
        menuDetailDto.setLight(false);

        // OrderMenuDto 객체 생성 및 초기화
        OrderMenuDto orderMenuDto = new OrderMenuDto();
        orderMenuDto.setMenuId(1);
        orderMenuDto.setQuantity(2);
        orderMenuDto.setMenuOptions(menuDetailDto);

        // OrderRequestDto의 orderMenuOptions 설정
        orderRequestDto.setOrderMenuOptions(Collections.singletonList(orderMenuDto));

        // Mocking: CategoryEntity 생성
        CategoryEntity mockCategory = new CategoryEntity();
        mockCategory.setId(1);
        mockCategory.setName("Beverage");

        // Mocking: MenuEntity 생성
        MenuEntity mockMenu = new MenuEntity("Americano", 5000, mockCategory);
        mockMenu.setMenuId(1);
        when(menuRepository.findMenuById(1)).thenReturn(mockMenu);

        // Mocking: MenuDetailEntity 저장
        MenuDetailEntity mockMenuDetail = new MenuDetailEntity();
        mockMenuDetail.setUseCup("TakeOut");
        mockMenuDetail.setAddShot(true);
        mockMenuDetail.setAddVanilla(true);
        mockMenuDetail.setAddHazelnut(false);
        mockMenuDetail.setLight(false);

        doAnswer(invocation -> {
            MenuDetailEntity savedEntity = invocation.getArgument(0);
            assertNotNull(savedEntity); // 저장하려는 엔티티가 null이 아님을 확인
            return null; // save는 void를 반환하므로 null 반환
        }).when(menuDetailRepository).save(any(MenuDetailEntity.class));

        // Mocking: OrderEntity 저장
        doAnswer(invocation -> {
            OrderEntity savedOrder = invocation.getArgument(0);
            savedOrder.setOrderId(1); // 저장 후 ID 설정
            savedOrder.setPickUp(LocalTime.of(10, 0)); // pickUp 값 설정
            return null;
        }).when(orderRepository).save(any(OrderEntity.class));

        // When: createOrder 호출
        OrderResponseDto result = orderService.createOrder(orderRequestDto);

        // Then: 결과 검증
        assertNotNull(result);
        assertEquals(1, result.getOrderId());
        assertEquals(10000, result.getTotalPrice());
        assertEquals(OrderStatus.WAITING, result.getOrderStatus());
        assertEquals(LocalTime.of(10, 0), result.getPickUp()); // PickUp 검증

        // Verify: 저장 메서드 호출 검증
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(orderMenuRepository, times(1)).saveAll(anyList());
        verify(menuRepository, times(1)).findMenuById(1);
        verify(menuDetailRepository, times(1)).save(any(MenuDetailEntity.class));
    }


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
        assertEquals(LocalTime.of(10, 0), response.getPickUp()); // PickUp 검증
        assertEquals(1, response.getOrderMenuOptions().size());

        OrderDto orderMenuDto = response.getOrderMenuOptions().get(0);
        assertEquals(1, orderMenuDto.getMenuId());
        assertEquals("Americano", orderMenuDto.getMenuName());
        assertEquals(2, orderMenuDto.getQuantity());

        // Verify: findByUserId 호출 검증
        verify(orderRepository, times(1)).findByUserId(1);
    }
}
