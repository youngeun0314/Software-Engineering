package Irumping.IrumOrder.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenuId implements Serializable {
    private Integer orderId;
    private Integer menuId;
    private Integer menu_detail_id; // Updated to match database column name

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderMenuId that = (OrderMenuId) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(menuId, that.menuId) &&
                Objects.equals(menu_detail_id, that.menu_detail_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, menuId, menu_detail_id);
    }
}