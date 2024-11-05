package Irumping.IrumOrder.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_menu")
public class OrderMenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 기본 키, order_id와 menu_id의 복합 키 대체 (식별용)

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order; // 주문과의 관계 (N:1)

    private int menuId; // MenuEntity의 ID 참조

    private int menuDetailId; // MenuDetailEntity의 ID 참조

    private int quantity = 1; // 수량 (기본값: 1)

    // 기본 생성자
    public OrderMenuEntity() {}

    // 생성자 (필요에 따라 사용자 정의 생성자를 추가할 수 있습니다)
}
