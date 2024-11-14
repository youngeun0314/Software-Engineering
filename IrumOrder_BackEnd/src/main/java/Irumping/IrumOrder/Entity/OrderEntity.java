package Irumping.IrumOrder.Entity;


import lombok.Getter;

import java.time.LocalTime;

@Getter
public class OrderEntity {

    private int orderId;
    private int userId;
    private int totalPrice;
    private OrderStatus orderStatus;
    private LocalTime pickUp;
    private boolean payment;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderMenuEntity> orderMenuOptions;

    public OrderEntity(int orderId, int userId, int totalPrice, OrderStatus orderStatus, LocalTime pickUp, boolean payment) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.pickUp = pickUp;
        this.payment = payment;
    }

    public void setOrderStatus(OrderStatus status) {
        this.orderStatus = status;
    }
}


//@Getter
//@Setter
//@Entity
//@Table(name = "orders")
//public class OrderEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int orderId;
//
//    private int userId;
//
//    private int totalPrice;
//
//    //    @Column(length = 20, nullable = false)
//    private OrderStatus orderStatus;
//
//    private LocalTime pickUp;
//
//    private boolean payment = false;
//
////    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
////    private List<OrderMenuEntity> orderMenuOptions;
//
//
//    public OrderEntity() {}
//
//    public OrderEntity(int userId, int totalPrice, OrderStatus orderStatus, LocalTime pickUp) {
//        this.userId = userId;
//        this.totalPrice = totalPrice;
//        this.orderStatus = orderStatus;
//        this.pickUp = pickUp;
////        this.payment = payment;
//        // totalPrice는 초기화하지 않음
//    }
//
//}
