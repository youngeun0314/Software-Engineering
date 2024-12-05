package Irumping.IrumOrder.listener;

import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.event.PickUpAlarmEvent;
import Irumping.IrumOrder.service.PickUpAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 클래스 설명: PickUpAlarmEventListener는 PickUpAlarmEvent를 처리하는 이벤트 리스너입니다.
 *             주문 상태가 READY_FOR_PICKUP으로 변경되었을 때 푸시 알림을 전송합니다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-06
 */
@Component
public class PickUpAlarmEventListener {

    @Autowired
    private PickUpAlarmService pickUpAlarmService;

    /**
     * PickUpAlarmEvent를 처리하여 사용자에게 푸시 알림을 전송합니다.
     *
     * @param event PickUpAlarmEvent 이벤트 객체
     */
    @EventListener
    public void onPickUpAlarmEvent(PickUpAlarmEvent event) {

        OrderStatus newStatus = event.getOrderEntity().getOrderStatus();

        // READY_FOR_PICKUP 상태일 때 알림 전송
        if (newStatus == OrderStatus.READY_FOR_PICKUP) {
            String title = "[" + event.getOrderEntity().getOrderId() + " 번] 조리완료";
            String body = "조리가 완료되었습니다. 주문을 픽업해주세요.";

            pickUpAlarmService.sendPushNotification(event.getOrderEntity().getUserId(), title, body);
        }
    }
}
