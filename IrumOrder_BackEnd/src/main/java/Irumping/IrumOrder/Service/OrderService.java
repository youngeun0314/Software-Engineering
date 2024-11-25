package Irumping.IrumOrder.service;

import Irumping.IrumOrder.dto.OrderRequestDto;
import Irumping.IrumOrder.dto.OrderResponseDto;
import Irumping.IrumOrder.dto.OrderMenuDto;
import Irumping.IrumOrder.dto.MenuDetailDto;
import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderMenuId;
import Irumping.IrumOrder.entity.OrderMenuEntity;
import Irumping.IrumOrder.entity.MenuDetailEntity;
import Irumping.IrumOrder.entity.MenuEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.repository.OrderRepository;
import Irumping.IrumOrder.repository.MenuRepository;
import Irumping.IrumOrder.repository.MenuDetailRepository;
import Irumping.IrumOrder.repository.OrderMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuDetailRepository menuDetailRepository;

    @Autowired
    private OrderMenuRepository orderMenuRepository;

    /**
     * 주문 생성: 클라이언트 요청으로 주문 생성
     */
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        logger.info("Start creating order...");

        // 1. OrderEntity 저장
        OrderEntity order = new OrderEntity(
                orderRequestDto.getUserId(),
                orderRequestDto.getTotalPrice(),
                OrderStatus.WAITING,  // 기본 주문 상태 설정
                orderRequestDto.getPickUp()
        );
        logger.debug("Saving OrderEntity: {}", order);
        orderRepository.save(order);

        // 2. 각 OrderMenuEntity 저장
        List<OrderMenuEntity> orderMenuOptions = orderRequestDto.getOrderMenuOptions().stream()
                .map(orderMenuDto -> createOrderMenuEntity(order, orderMenuDto))
                .collect(Collectors.toList());
        orderMenuRepository.saveAll(orderMenuOptions);

        // OrderEntity를 OrderResponseDto로 변환하여 반환
        return convertToResponseDto(order);
    }

    /**
     * OrderMenuEntity 생성 메서드
     */
    private OrderMenuEntity createOrderMenuEntity(OrderEntity order, OrderMenuDto orderMenuDto) {
        OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
        orderMenuEntity.setOrder(order);  // OrderEntity와 매핑

        // Menu 설정
        MenuEntity menu = menuRepository.findById(orderMenuDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("Menu ID가 잘못되었습니다: " + orderMenuDto.getMenuId()));
        orderMenuEntity.setMenu(menu);

        // MenuDetailEntity 생성 및 저장
        MenuDetailDto menuOptions = orderMenuDto.getMenuOptions();
        MenuDetailEntity menuDetailEntity = new MenuDetailEntity(
                menuOptions.getUseCup(),
                menuOptions.getAddShot(),
                menuOptions.getAddVanilla(),
                menuOptions.getAddHazelnut(),
                menuOptions.getLight()
        );
        menuDetailRepository.save(menuDetailEntity);
        orderMenuEntity.setMenuDetail(menuDetailEntity);
        orderMenuEntity.setQuantity(orderMenuDto.getQuantity());

        // 복합 키 설정
        OrderMenuId orderMenuId = new OrderMenuId(
                order.getOrderId(),
                menu.getMenuId(),
                menuDetailEntity.getMenuDetailId()
        );
        orderMenuEntity.setId(orderMenuId);

        return orderMenuEntity;
    }

    /**
     * OrderEntity를 OrderResponseDto로 변환
     */
    public OrderResponseDto convertToResponseDto(OrderEntity orderEntity) {
        OrderResponseDto responseDto = new OrderResponseDto();

        responseDto.setOrderId(orderEntity.getOrderId());
        responseDto.setUserId(orderEntity.getUserId());
        responseDto.setTotalPrice(orderEntity.getTotalPrice());
        responseDto.setOrderStatus(OrderStatus.valueOf(orderEntity.getOrderStatus().toString()));
        responseDto.setPayment(orderEntity.getPayment());

        // OrderMenuDto 리스트로 변환
        responseDto.setOrderMenuOptions(orderEntity.getOrderMenuOptions().stream()
                .map(this::convertToOrderMenuDto)
                .collect(Collectors.toList()));

        return responseDto;
    }

    private OrderMenuDto convertToOrderMenuDto(OrderMenuEntity orderMenuEntity) {
        OrderMenuDto orderMenuDto = new OrderMenuDto();
        orderMenuDto.setMenuId(orderMenuEntity.getMenu().getMenuId());
        orderMenuDto.setQuantity(orderMenuEntity.getQuantity());

        // MenuDetailEntity를 MenuDetailDto로 변환하여 설정
        MenuDetailDto menuDetailDto = new MenuDetailDto();
        menuDetailDto.setUseCup(orderMenuEntity.getMenuDetail().getUseCup());
        menuDetailDto.setAddShot(orderMenuEntity.getMenuDetail().isAddShot());
        menuDetailDto.setAddVanilla(orderMenuEntity.getMenuDetail().isAddVanilla());
        menuDetailDto.setAddHazelnut(orderMenuEntity.getMenuDetail().isAddHazelnut());
        menuDetailDto.setLight(orderMenuEntity.getMenuDetail().isLight());
        orderMenuDto.setMenuOptions(menuDetailDto);

        return orderMenuDto;
    }
}
