package Irumping.IrumOrder.service;

import Irumping.IrumOrder.dto.*;
import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderMenuId;
import Irumping.IrumOrder.entity.OrderMenuEntity;
import Irumping.IrumOrder.entity.MenuDetailEntity;
import Irumping.IrumOrder.entity.MenuEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.repository.JpaMenuRepository;
import Irumping.IrumOrder.repository.OrderRepository;
import Irumping.IrumOrder.repository.MenuDetailRepository;
import Irumping.IrumOrder.repository.OrderMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private JpaMenuRepository menuRepository;

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
        MenuEntity menu = menuRepository.findMenuById(orderMenuDto.getMenuId());
        if(menu == null){
            throw new IllegalArgumentException("Menu ID가 잘못되었습니다: " + orderMenuDto.getMenuId());
        }
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

    /**
     * 사용자의 모든 주문을 조회하는 메서드.
     *
     * @param userId 사용자 ID
     * @return OrdersCheckResponseDto 사용자의 주문 정보
     */
    public List<OrdersCheckResponseDto> getOrdersByUserId(Integer userId) {
        // 사용자의 주문 목록 조회
        List<OrderEntity> orders = orderRepository.findByUserId(userId);

        // 결과 변환
        return orders.stream().map(order -> {
            // OrderMenuEntity에서 데이터를 추출하여 OrderDto로 변환
            List<OrderDto> orderMenuOptions = order.getOrderMenuOptions().stream().map(orderMenu -> {
                MenuDetailEntity menuDetail = orderMenu.getMenuDetail();
                MenuEntity menu = orderMenu.getMenu();
                return new OrderDto(
                        menu.getMenuId(),
                        menu.getName(),
                        orderMenu.getQuantity(),
                        menuDetail.getUseCup(),
                        menuDetail.isAddShot(),
                        menuDetail.isAddVanilla(),
                        menuDetail.isAddHazelnut(),
                        menuDetail.isLight()
                );
            }).collect(Collectors.toList());

            // OrdersCheckResponseDto 생성
            return new OrdersCheckResponseDto(
                    order.getOrderId(),
                    order.getUserId(),
                    order.getTotalPrice(),
                    order.getOrderStatus(),
                    order.getPickUp(),
                    orderMenuOptions
            );
        }).collect(Collectors.toList());
    }
}
