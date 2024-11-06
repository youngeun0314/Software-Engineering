package Irumping.IrumOrder.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/* order_menu 테이블과 매핑됨 */
@Getter
@Setter
@Entity
@Table(name = "order_menu")
public class OrderMenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //고유 엔티티 사용 식별 id일뿐 디비에 매핑 x

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuEntity menu;

    @ManyToOne
    @JoinColumn(name = "menuDetail_id")
    private MenuDetailEntity menuDetail;

    private int quantity = 1;

    public OrderMenuEntity() {}
}
