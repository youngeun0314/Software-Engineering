package Irumping.IrumOrder.event;

import Irumping.IrumOrder.entity.OrderEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 클래스 설명: PickUpAlarmEvent는 주문(OrderEntity) 정보를 기반으로 발생하는 이벤트를
 *             정의합니다. Spring의 이벤트 시스템을 사용하여 특정 동작을 트리거합니다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-06
 */
@Getter
public class PickUpAlarmEvent extends ApplicationEvent {

    private final OrderEntity orderEntity;

    /**
     * PickUpAlarmEvent 생성자.
     *
     * @param source      이벤트 발생의 소스 객체
     * @param orderEntity 이벤트와 연관된 주문 엔티티
     */
    public PickUpAlarmEvent(Object source, OrderEntity orderEntity) {
        super(source); // ApplicationEvent 생성자 호출
        this.orderEntity = orderEntity;
    }
}
