package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.OrderMenuEntity;
import Irumping.IrumOrder.Entity.OrderMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenuEntity, OrderMenuId> {
    // 필요한 커스텀 메서드가 있다면 추가로 정의할 수 있습니다.
}
