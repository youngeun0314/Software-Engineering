package Irumping.IrumOrder.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * 클래스 설명: 복합 키를 나타내는 Embeddable 클래스.
 * 주문 ID, 메뉴 ID, 메뉴 세부 정보 ID를 포함하여 OrderMenuEntity의 복합 키로 사용된다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenuId implements Serializable {

    private Integer orderId;

    private Integer menuId;

    private Integer menu_detail_id;

    /**
     * 두 객체를 비교하여 동등성을 검사하는 메서드.
     *
     * @param o 비교할 객체
     * @return 두 객체가 동일하면 true, 아니면 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderMenuId that = (OrderMenuId) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(menuId, that.menuId) &&
                Objects.equals(menu_detail_id, that.menu_detail_id);
    }

    /**
     * 객체의 해시코드를 생성하는 메서드.
     *
     * @return 객체의 해시코드 값
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, menuId, menu_detail_id);
    }
}
