//package Irumping.IrumOrder.service;
//
//import Irumping.IrumOrder.entity.OrderEntity;
//import Irumping.IrumOrder.entity.OrderStatus;
//import Irumping.IrumOrder.event.PickUpAlarmEvent;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationEventPublisher;
//
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class PickUpAlarmServiceTest {
//
//    @Autowired
//    private PickUpAlarmService pickUpAlarmService;
//
//    @Autowired
//    private ApplicationEventPublisher eventPublisher;
//
//    @Test
//    void testSendPushNotification() {
//        // Mock OrderEntity
//        OrderEntity mockOrder = new OrderEntity();
//        mockOrder.setOrderId(123);
//        mockOrder.setOrderStatus(OrderStatus.READY_FOR_PICKUP);
//        mockOrder.setUserId(456L);
//
//        // Mock PickUpAlarmEvent
//        PickUpAlarmEvent event = new PickUpAlarmEvent(this, mockOrder);
//
//        // Simulate event publishing
//        eventPublisher.publishEvent(event);
//
//        // Verify that the notification method in the service is called
//        pickUpAlarmService.sendPushNotification(
//                mockOrder.getUserId(),
//                "[ 123 번] 조리완료",
//                "조리가 완료되었습니다. 주문을 픽업해주세요."
//        );
//
//        // You can mock dependencies of PickUpAlarmService to verify further behavior
//        // e.g., ensure the push notification API was called
//    }
//}
