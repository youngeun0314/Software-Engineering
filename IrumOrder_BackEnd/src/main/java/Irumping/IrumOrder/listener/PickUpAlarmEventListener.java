package Irumping.IrumOrder.listener;

import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.event.PickUpAlarmEvent;
import Irumping.IrumOrder.service.PickUpAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class PickUpAlarmEventListener {

    @Autowired
    private PickUpAlarmService pickUpAlarmService;

    @EventListener
    public void onPickUpAlarmEvent(PickUpAlarmEvent event) {

        OrderStatus newStatus = event.getOrderEntity().getOrderStatus();

        // READY_FOR_PICKUP 상태일 때 알림 전송
        if (newStatus == OrderStatus.READY_FOR_PICKUP) {
            String title = "["+ event.getOrderEntity().getOrderId() +" 번] 조리완료";
            String body = "조리가 완료되었습니다. 주문을 픽업해주세요.";

            pickUpAlarmService.sendPushNotification(event.getOrderEntity().getUserId(), title, body);
        }
    }
}
