package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Dto.OrderRequestDto;
import Irumping.IrumOrder.Dto.MenuDetailDto;
import Irumping.IrumOrder.Dto.OrderMenuDto;
import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.OrderMenuEntity;
import Irumping.IrumOrder.Entity.MenuDetailEntity;
import Irumping.IrumOrder.Repository.OrderRepository;
import Irumping.IrumOrder.Repository.OrderMenuRepository;
import Irumping.IrumOrder.Repository.MenuRepository;
import Irumping.IrumOrder.Repository.MenuDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

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
    public OrderEntity createOrder(OrderRequestDto orderRequestDto) {
        // OrderEntity 생성
        OrderEntity order = new OrderEntity(
                orderRequestDto.getUserId(),
                orderRequestDto.getTotalPrice(),
                "예약대기",  // 기본 주문 상태 설정
                orderRequestDto.getPickUp()
//                false
        );

        // 주문에 포함된 메뉴 항목들 설정
        List<OrderMenuEntity> orderMenuOptions = orderRequestDto.getOrderMenuOptions().stream().map(orderMenuDto -> {
            OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
            orderMenuEntity.setOrder(order);
            //createOrder에서 orderEntity를 생성하므로 이렇게해서 바로 설정할 수 있음
            //orders 테이블을 직접 입력해야하므로 이렇게 하는게 맞음
            orderMenuEntity.setMenu(menuRepository.findById(orderMenuDto.getMenuId())
                    .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다.")));
            //menu는 이미 저장되어잇는 데베를 조회하기만 하는것이므로 id만 조회해서 실제 데이터베이스에 존재하는건지 확인하는 과정

            //menuDetail 설정
            MenuDetailDto menuOptions = orderMenuDto.getMenuOptions();  // MenuDetailDto
            MenuDetailEntity menuDetailEntity = new MenuDetailEntity(
                    menuOptions.getUseCup(),
                    menuOptions.getAddShot(),
                    menuOptions.getAddVanilla(),
                    menuOptions.getAddHazelnut(),
                    menuOptions.getLight()
            );

            menuDetailRepository.save(menuDetailEntity);
            orderMenuEntity.setMenuDetail(menuDetailEntity); //orderMenu 테이블에 외래키로 menuDetail 설정
            orderMenuEntity.setQuantity(orderMenuDto.getQuantity());
            return orderMenuEntity;
        }).toList();


        orderMenuRepository.saveAll(orderMenuOptions); //orderMenuRepository에 저장

        // 설정한 메뉴 옵션 리스트를 OrderEntity에 추가
        order.setOrderMenuOptions(orderMenuOptions); //order테이블 orderMenu테이블 연결

        // OrderEntity를 저장하고 반환
        return orderRepository.save(order);

    }
}
