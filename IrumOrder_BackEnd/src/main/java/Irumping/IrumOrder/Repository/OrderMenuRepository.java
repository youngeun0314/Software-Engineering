package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.OrderMenuEntity;
import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenuEntity, OrderMenuEntity.OrderMenuId> {
}
