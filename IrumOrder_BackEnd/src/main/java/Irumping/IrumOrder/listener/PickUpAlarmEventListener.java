package Irumping.IrumOrder.listener;

import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.event.PickUpAlarmEvent;
import Irumping.IrumOrder.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PickUpAlarmEventListener {

    @Autowired
    private AlarmService alarmService;

    @EventListener
    public void onPickUpAlarmEvent(PickUpAlarmEvent event) {
        OrderStatus newStatus = event.getOrderEntity().getOrderStatus();

        // READY_FOR_PICKUP 상태일 때 알림 전송
        if (newStatus == OrderStatus.READY_FOR_PICKUP) {
            String title = "Order Ready for Pickup";
            String body = "Your order is ready for pickup! Please come and collect it.";
            String userToken = getUserFirebaseToken(event.getOrderEntity().getUserId());

            alarmService.sendPushNotification(title, body, userToken);
        }
    }

    private String getUserFirebaseToken(Integer userId) {
        // Firebase 토큰을 가져오는 로직 구현 (예: DB 조회)
        return "mock_firebase_token"; // 임시 토큰 반환
    }
}
