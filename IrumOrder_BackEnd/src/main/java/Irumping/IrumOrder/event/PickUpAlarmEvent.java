package Irumping.IrumOrder.event;

import Irumping.IrumOrder.entity.OrderEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PickUpAlarmEvent extends ApplicationEvent {

    private final OrderEntity orderEntity;

    public PickUpAlarmEvent(Object source, OrderEntity orderEntity) {
        super(source); // ApplicationEvent 생성자 호출
        this.orderEntity = orderEntity;
    }
}
