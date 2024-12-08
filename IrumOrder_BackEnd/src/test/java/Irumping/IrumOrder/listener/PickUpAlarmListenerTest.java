package Irumping.IrumOrder.listener;

import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.event.PickUpAlarmEvent;
import Irumping.IrumOrder.service.PickUpAlarmService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.Mockito.*;

@SpringJUnitConfig
class PickUpAlarmEventListenerTest {

    @InjectMocks
    private PickUpAlarmEventListener pickUpAlarmEventListener;

    @Mock
    private PickUpAlarmService pickUpAlarmService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    void testOnPickUpAlarmEvent() {

        OrderEntity mockOrder = new OrderEntity();
        mockOrder.setOrderId(123);
        mockOrder.setOrderStatus(OrderStatus.READY_FOR_PICKUP);
        mockOrder.setUserId(456);

        PickUpAlarmEvent event = new PickUpAlarmEvent(this, mockOrder);

        pickUpAlarmEventListener.onPickUpAlarmEvent(event);

        verify(pickUpAlarmService, times(1)).sendPushNotification(
                eq(mockOrder.getUserId()),
                eq("[123 번] 조리완료"),
                eq("조리가 완료되었습니다. 주문을 픽업해주세요.")
        );
    }
}
