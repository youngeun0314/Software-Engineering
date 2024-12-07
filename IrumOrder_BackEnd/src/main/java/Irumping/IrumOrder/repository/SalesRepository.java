package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
/**
 * 인터페이스 설명: 주문 데이터를 관리하고 매출 정보를 조회하는 리포지토리 인터페이스.
 * Spring Data JPA를 활용하여 데이터베이스와 상호작용합니다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-01
 */
public interface SalesRepository extends JpaRepository<OrderEntity, Long> {
    /**
     * 특정 날짜의 매출 총합을 조회하는 메서드.
     * 결제 시간이 지정된 날짜 범위에 속하는 주문의 총 금액을 반환한다.
     *
     * @param startOfDay 조회할 날짜의 시작 시각
     * @param endOfDay 조회할 날짜의 끝 시각
     * @return Optional<Integer> 조회된 매출 총합 (없을 경우 빈 Optional 반환)
     */
    @Query("SELECT SUM(o.totalPrice) FROM OrderEntity o WHERE o.payment >= :startOfDay AND o.payment < :endOfDay")
    Optional<Integer> findTotalSalesByDate(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}