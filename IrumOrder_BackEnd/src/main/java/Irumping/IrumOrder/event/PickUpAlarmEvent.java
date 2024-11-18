package Irumping.IrumOrder.event;

import Irumping.IrumOrder.entity.OrderEntity;
import org.springframework.context.ApplicationEvent;

public class PickUpAlarmEvent extends ApplicationEvent {

    private final OrderEntity orderEntity;

    public PickUpAlarmEvent(Object source, OrderEntity orderEntity) {
        super(source);
        this.orderEntity = orderEntity;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }
}
