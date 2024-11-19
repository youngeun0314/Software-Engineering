package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SalesRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT SUM(o.totalPrice) FROM OrderEntity o WHERE o.payDatetime >= :startOfDay AND o.payDatetime < :endOfDay")
    Optional<Integer> findTotalSalesByDate(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}