package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.OrderMenuEntity;
import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenuEntity, Integer> {

    // 특정 주문에 포함된 모든 메뉴를 조회
    List<OrderMenuEntity> findByOrder(OrderEntity order);

    // 특정 메뉴가 포함된 모든 주문 조회
    List<OrderMenuEntity> findByMenu(MenuEntity menu);

    // 특정 주문에 포함된 특정 메뉴 조회 (옵션까지 포함)
    List<OrderMenuEntity> findByOrderAndMenu(OrderEntity order, MenuEntity menu);
}
