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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMenuRepository orderMenuRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuDetailRepository menuDetailRepository;

    /**
     * 주문 생성: 클라이언트 요청으로 주문 생성
     */
    @Transactional
    public OrderEntity createOrder(OrderRequestDto orderRequestDto) {
        OrderEntity order = new OrderEntity();
        order.setUserId(orderRequestDto.getUserId());
        order.setTotalPrice(orderRequestDto.getTotalPrice());
        order.setPickUp(orderRequestDto.getPickUp());
        order.setPayment(false); // 결제 상태는 false

        List<OrderMenuEntity> orderMenuOptions = orderRequestDto.getOrderMenuOptions().stream().map(orderMenuDto -> {
            OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
            orderMenuEntity.setOrder(order);
            orderMenuEntity.setMenu(menuRepository.findById(orderMenuDto.getMenuId())
                    .orElseThrow(() -> new RuntimeException("Menu not found")));
            orderMenuEntity.setMenuDetail(menuDetailRepository.findById(orderMenuDto.getMenuDetailId())
                    .orElse(null));
            orderMenuEntity.setQuantity(orderMenuDto.getQuantity());
            return orderMenuEntity;
        }).toList();

        order.setOrderMenuOptions(orderMenuOptions);
        return orderRepository.save(order);  // Save and return the order entity
    }
}
