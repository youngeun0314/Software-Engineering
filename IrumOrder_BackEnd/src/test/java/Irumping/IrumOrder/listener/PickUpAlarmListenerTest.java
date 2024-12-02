//package Irumping.IrumOrder.listener;
//
//import Irumping.IrumOrder.entity.OrderEntity;
//import Irumping.IrumOrder.entity.OrderStatus;
//import Irumping.IrumOrder.event.PickUpAlarmEvent;
//import Irumping.IrumOrder.service.PickUpAlarmService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class PickUpAlarmEventListenerTest {
//
//    @Autowired
//    private PickUpAlarmEventListener pickUpAlarmEventListener;
//
//    @Autowired
//    private PickUpAlarmService pickUpAlarmService;
//
//    @Test
//    void testOnPickUpAlarmEvent() {
//        // Mock OrderEntity
//        OrderEntity mockOrder = new OrderEntity();
//        mockOrder.setOrderId(123);
//        mockOrder.setOrderStatus(OrderStatus.READY_FOR_PICKUP);
//        mockOrder.setUserId(456L);
//
//        // Mock PickUpAlarmEvent
//        PickUpAlarmEvent event = new PickUpAlarmEvent(this, mockOrder);
//
//        // Spy the service to verify interaction
//        PickUpAlarmService spyService = spy(pickUpAlarmService);
//
//        // Trigger the event listener
//        pickUpAlarmEventListener.onPickUpAlarmEvent(event);
//
//        // Verify that the service method is called with the correct arguments
//        verify(spyService).sendPushNotification(
//                mockOrder.getUserId(),
//                "[ 123 번] 조리완료",
//                "조리가 완료되었습니다. 주문을 픽업해주세요."
//        );
//    }
//}
