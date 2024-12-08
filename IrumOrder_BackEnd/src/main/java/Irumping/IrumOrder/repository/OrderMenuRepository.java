package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderMenuEntity;
import Irumping.IrumOrder.entity.OrderMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMenuRepository {
    List<OrderMenuEntity> findByOrder_OrderId(Integer orderId);

    void saveAll(List<OrderMenuEntity> orderMenuOptions);
}
