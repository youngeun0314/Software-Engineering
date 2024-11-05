package Irumping.IrumOrder.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private int userId;

    private int totalPrice;

    @Column(length = 20, nullable = false)
    private String orderStatus = "예약대기"; //(예약대기,주분접수,상품준비완료,완료)

    private LocalTime pickUp;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenuEntity> orderMenuOptions;

    public OrderEntity(){}
}
