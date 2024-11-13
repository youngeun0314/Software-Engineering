package Irumping.IrumOrder.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

public class OrderEntity {

//    @Getter
//    @Setter
//    @Entity
//    @Table(name = "orders")
//    public class OrderEntity {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private int orderId;
//
//        private int userId;
//
//        private int totalPrice;
//
//        //    @Column(length = 20, nullable = false)
//        private OrderStatus orderStatus;
//
//        private LocalTime pickUp;
//
//        private boolean payment = false;
//
//        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//        private List<OrderMenuEntity> orderMenuOptions;
//
//
//        public OrderEntity() {}
//
//        public OrderEntity(int userId, int totalPrice, OrderStatus orderStatus, LocalTime pickUp) {
//            this.userId = userId;
//            this.totalPrice = totalPrice;
//            this.orderStatus = orderStatus;
//            this.pickUp = pickUp;
////        this.payment = payment;
//            // totalPrice는 초기화하지 않음
//        }

}
