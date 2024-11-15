package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Dto.OrderRequestDto;
import Irumping.IrumOrder.Dto.OrderResponseDto;
import Irumping.IrumOrder.Dto.OrderMenuDto;
import Irumping.IrumOrder.Dto.MenuDetailDto;
import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.OrderMenuId;
import Irumping.IrumOrder.Entity.OrderMenuEntity;
import Irumping.IrumOrder.Entity.MenuDetailEntity;
import Irumping.IrumOrder.Entity.MenuEntity;
import Irumping.IrumOrder.Entity.OrderStatus;
import Irumping.IrumOrder.Repository.OrderRepository;
import Irumping.IrumOrder.Repository.MenuRepository;
import Irumping.IrumOrder.Repository.MenuDetailRepository;
import Irumping.IrumOrder.Repository.OrderMenuRepository;
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

        // 주문 픽업 시간을 LocalTime 형식으로 변환
        LocalTime pickUpTime = LocalTime.parse(orderRequestDto.getPickUp(), DateTimeFormatter.ofPattern("HH:mm:ss"));

        // 1. OrderEntity 저장
        OrderEntity order = new OrderEntity(
                orderRequestDto.getUserId(),
                orderRequestDto.getTotalPrice(),
                OrderStatus.WAITING,  // 기본 주문 상태 설정
                pickUpTime
        );
        logger.debug("Saving OrderEntity: {}", order);

        // OrderEntity 저장
        saveOrderWithRetry(order);

        // 2. 각 MenuDetailEntity를 생성하여 저장하고, OrderMenuEntity에 매핑
        List<OrderMenuEntity> orderMenuOptions = orderRequestDto.getOrderMenuOptions().stream().map(orderMenuDto -> {
            OrderMenuEntity orderMenuEntity = createOrderMenuEntity(order, orderMenuDto);
            return orderMenuEntity;
        }).toList();

        // 3. 중복 방지 로직: 존재하지 않을 때만 저장
        saveOrderMenuEntitiesWithRetry(orderMenuOptions);

        // OrderEntity를 OrderResponseDto로 변환하여 반환
        return convertToResponseDto(order);
    }

    /**
     * OrderEntity를 데이터베이스에 저장하고 재시도 로직 추가
     */
    private void saveOrderWithRetry(OrderEntity order) {
        try {
            orderRepository.save(order);
            orderRepository.flush(); // 저장 후 즉시 flush
            logger.info("OrderEntity saved with ID: {}", order.getOrderId());
        } catch (Exception e) {
            logger.error("Failed to save OrderEntity", e);
            throw new RuntimeException("Order 저장 실패");
        }
    }

    /**
     * 각 OrderMenuEntity를 데이터베이스에 저장하고 중복 방지 및 재시도 로직 추가
     */
    private void saveOrderMenuEntitiesWithRetry(List<OrderMenuEntity> orderMenuEntities) {
        for (OrderMenuEntity orderMenuEntity : orderMenuEntities) {
            if (!orderMenuRepository.existsById(orderMenuEntity.getId())) {
                try {
                    orderMenuRepository.save(orderMenuEntity);
                    orderMenuRepository.flush(); // 저장 후 즉시 flush
                    logger.debug("OrderMenuEntity saved: {}", orderMenuEntity);
                } catch (Exception e) {
                    logger.error("Failed to save OrderMenuEntity with ID: {}", orderMenuEntity.getId(), e);
                    throw new RuntimeException("OrderMenu 저장 실패");
                }
            } else {
                logger.warn("OrderMenuEntity with ID {} already exists and will not be duplicated.", orderMenuEntity.getId());
            }
        }
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
        logger.debug("MenuEntity set for OrderMenuEntity: {}", menu);

        // MenuDetailEntity 생성 및 저장
        MenuDetailDto menuOptions = orderMenuDto.getMenuOptions();
        MenuDetailEntity menuDetailEntity = new MenuDetailEntity(
                menuOptions.getUseCup(),
                menuOptions.getAddShot(),
                menuOptions.getAddVanilla(),
                menuOptions.getAddHazelnut(),
                menuOptions.getLight()
        );

        // MenuDetailEntity 저장
        saveMenuDetailEntityWithRetry(menuDetailEntity);
        orderMenuEntity.setMenuDetail(menuDetailEntity);
        orderMenuEntity.setQuantity(orderMenuDto.getQuantity());

        // 복합 키 설정
        OrderMenuId orderMenuId = new OrderMenuId(
                order.getOrderId(),
                menu.getMenuId(),
                menuDetailEntity.getMenuDetailId()
        );
        orderMenuEntity.setId(orderMenuId);
        logger.debug("OrderMenuEntity configured with ID: {}", orderMenuId);

        return orderMenuEntity;
    }

    /**
     * MenuDetailEntity 저장과 재시도 로직 추가
     */
    private void saveMenuDetailEntityWithRetry(MenuDetailEntity menuDetailEntity) {
        try {
            menuDetailRepository.save(menuDetailEntity);
            menuDetailRepository.flush(); // 저장 후 즉시 flush
            logger.info("MenuDetailEntity saved with ID: {}", menuDetailEntity.getMenuDetailId());
        } catch (Exception e) {
            logger.error("Failed to save MenuDetailEntity", e);
            throw new RuntimeException("MenuDetail 저장 실패");
        }
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
        responseDto.setPayment(orderEntity.isPayment());

        // LocalTime을 문자열로 변환하여 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        responseDto.setPickUp(orderEntity.getPickUp() != null ? orderEntity.getPickUp().format(formatter) : null);

        // OrderMenuDto 리스트로 변환
        responseDto.setOrderMenuOptions(orderEntity.getOrderMenuOptions().stream()
                .map(this::convertToOrderMenuDto)
                .collect(Collectors.toList()));

        logger.debug("Converted OrderEntity to OrderResponseDto: {}", responseDto);
        return responseDto;
    }

    /**
     * OrderMenuEntity를 OrderMenuDto로 변환하는 메서드
     */
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

        logger.debug("Converted OrderMenuEntity to OrderMenuDto: {}", orderMenuDto);
        return orderMenuDto;
    }
}
