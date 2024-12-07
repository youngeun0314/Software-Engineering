package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_menu")
public class OrderMenuEntity {

    @EmbeddedId
    private OrderMenuId id = new OrderMenuId();

    @ManyToOne
    @MapsId("orderId") // Mapping to composite key
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @MapsId("menuId") // Mapping to composite key
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuEntity menu;

    @ManyToOne(optional = false)
    @MapsId("menu_detail_id") // Mapping to composite key, adjusted to match schema
    @JoinColumn(name = "menu_detail_id", nullable = false)
    private MenuDetailEntity menuDetail;

    @Column(name = "quantity", nullable = false)
    private int quantity = 1;

    public OrderMenuEntity() {}

    public OrderMenuEntity(OrderEntity order, MenuEntity menu, MenuDetailEntity menuDetail, int quantity) {
        this.order = order;
        this.menu = menu;
        this.menuDetail = menuDetail;
        this.quantity = quantity;
        this.id = new OrderMenuId(order.getOrderId(), menu.getMenuId(), menuDetail.getMenuDetailId());
    }
}
