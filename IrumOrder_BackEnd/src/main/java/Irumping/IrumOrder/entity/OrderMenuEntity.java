package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 클래스 설명: 주문 메뉴 정보를 담는 엔티티.
 * 주문, 메뉴, 메뉴 세부 정보를 포함하며, 각각의 수량을 관리한다.
 * 다대다 관계를 다루기 위해 복합 키를 사용한다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@Getter
@Setter
@Entity
@Table(name = "order_menu")
public class OrderMenuEntity {

    @EmbeddedId
    private OrderMenuId id = new OrderMenuId();

    @ManyToOne
    @MapsId("orderId") // 복합 키 매핑: 주문 ID
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @MapsId("menuId") // 복합 키 매핑: 메뉴 ID
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuEntity menu;

    @ManyToOne(optional = false)
    @MapsId("menu_detail_id") // 복합 키 매핑: 메뉴 세부 정보 ID
    @JoinColumn(name = "menu_detail_id", nullable = false)
    private MenuDetailEntity menuDetail;

    @Column(name = "quantity", nullable = false)
    private int quantity = 1;

    /**
     * 기본 생성자.
     */
    public OrderMenuEntity() {}

    /**
     * OrderMenuEntity 초기화 생성자.
     *
     * @param order      주문 엔티티
     * @param menu       메뉴 엔티티
     * @param menuDetail 메뉴 세부 정보 엔티티
     * @param quantity   주문 수량
     */
    public OrderMenuEntity(OrderEntity order, MenuEntity menu, MenuDetailEntity menuDetail, int quantity) {
        this.order = order;
        this.menu = menu;
        this.menuDetail = menuDetail;
        this.quantity = quantity;
        this.id = new OrderMenuId(order.getOrderId(), menu.getMenuId(), menuDetail.getMenuDetailId());
    }
}
