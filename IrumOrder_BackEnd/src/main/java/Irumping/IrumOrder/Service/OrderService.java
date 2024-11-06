package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Dto.OrderRequestDto;
import Irumping.IrumOrder.Dto.OrderMenuDto;
import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.OrderMenuEntity;
import Irumping.IrumOrder.Entity.MenuEntity;
import Irumping.IrumOrder.Repository.OrderRepository;
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
            orderMenuEntity.setMenu(menuRepository.findById(orderMenuDto.getMenuId())
                    .orElseThrow(() -> new RuntimeException("Menu not found")));

            // menuDetailId를 이용해 MenuDetailEntity를 조회하고 설정
            orderMenuEntity.setMenuDetail(menuDetailRepository.findById(orderMenuDto.getMenuDetailId())
                    .orElse(null));

            orderMenuEntity.setQuantity(orderMenuDto.getQuantity());
            return orderMenuEntity;
        }).toList();

        // 설정한 메뉴 옵션 리스트를 OrderEntity에 추가
        order.setOrderMenuOptions(orderMenuOptions);

        // OrderEntity를 저장하고 반환
        return orderRepository.save(order);

        //-> 굳이반환할필요없고 성공적으로 저장됏는지아닌지
    }

    /*
    * 유저가 자신의 주문내역 조회 가능
    * */
    public List<OrderEntity> findByUserId(int userId){

    }
}
