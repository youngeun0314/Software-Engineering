package Irumping.IrumOrder.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "order_menu")
public class OrderMenuEntity {

    @EmbeddedId
    private OrderMenuId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @MapsId("menuId")
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuEntity menu;

    @ManyToOne
    @MapsId("menuDetailId")
    @JoinColumn(name = "menuDetail_id")
    private MenuDetailEntity menuDetail;

    private int quantity = 1;

    public OrderMenuEntity() {}

    @Embeddable
    public static class OrderMenuId implements Serializable {

        private int orderId;
        private int menuId;
        private int menuDetailId;

        // 기본 생성자
        public OrderMenuId() {}

        public OrderMenuId(int orderId, int menuId, int menuDetailId) {
            this.orderId = orderId;
            this.menuId = menuId;
            this.menuDetailId = menuDetailId;
        }

        // equals 및 hashCode 메서드
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderMenuId that = (OrderMenuId) o;
            return orderId == that.orderId && menuId == that.menuId && menuDetailId == that.menuDetailId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, menuId, menuDetailId);
        }
    }
}
