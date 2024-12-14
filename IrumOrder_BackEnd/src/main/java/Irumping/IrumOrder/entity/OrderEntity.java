package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 클래스 설명: 주문 정보를 담는 엔티티.
 * 주문 ID, 사용자 ID, 총 금액, 주문 상태, 픽업 시간, 결제 시간 등의 정보를 관리한다.
 * 또한 주문에 포함된 메뉴 옵션 목록도 관리한다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", length = 50, nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "pick_up")
    private LocalTime pickUp;

    @Column(name = "payment")
    private LocalDateTime payment = null;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderMenuEntity> orderMenuOptions = new ArrayList<>();

    @Column(name = "tid")
    private String tid;

    /**
     * 기본 생성자.
     */
    public OrderEntity() {}

    /**
     * 주문 정보를 초기화하는 생성자.
     *
     * @param userId      사용자 ID
     * @param totalPrice  주문 총 금액
     * @param orderStatus 주문 상태
     * @param pickUp      픽업 시간
     */
    public OrderEntity(Integer userId, Integer totalPrice, OrderStatus orderStatus, LocalTime pickUp) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.pickUp = pickUp;
    }
}
