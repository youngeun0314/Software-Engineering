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

/**
 * PickUpAlarmEventListenerTest
 *
 * 이 클래스는 PickUpAlarmEventListener의 동작을 테스트하기 위한 단위 테스트 클래스입니다.
 * 이벤트가 발생했을 때 알림 서비스가 올바르게 호출되는지를 검증합니다.
 *
 * 작성자: 김은지
 * 작성일: 2024-12-12
 */
@SpringJUnitConfig
class PickUpAlarmEventListenerTest {

    @InjectMocks
    private PickUpAlarmEventListener pickUpAlarmEventListener; // 테스트 대상 클래스

    @Mock
    private PickUpAlarmService pickUpAlarmService; // 알림 전송 서비스 Mock

    @Mock
    private ApplicationEventPublisher eventPublisher; // 이벤트 퍼블리셔 Mock

    /**
     * 픽업 알람 이벤트 발생 시 알림 서비스가 호출되는지 테스트합니다.
     * 이 테스트는 이벤트 리스너의 동작을 검증합니다.
     */
    @Test
    void testOnPickUpAlarmEvent() {
        // Given: Mock 데이터 생성
        OrderEntity mockOrder = new OrderEntity();
        mockOrder.setOrderId(123); // 주문 ID
        mockOrder.setOrderStatus(OrderStatus.READY_FOR_PICKUP); // 주문 상태
        mockOrder.setUserId(456); // 사용자 ID

        // PickUpAlarmEvent 생성
        PickUpAlarmEvent event = new PickUpAlarmEvent(this, mockOrder);

        // When: 이벤트 리스너 호출
        pickUpAlarmEventListener.onPickUpAlarmEvent(event);

        // Then: 알림 서비스가 올바르게 호출되었는지 검증
        verify(pickUpAlarmService, times(1)).sendPushNotification(
                eq(mockOrder.getUserId()), // 사용자 ID
                eq("[123 번] 조리완료"), // 알림 제목
                eq("조리가 완료되었습니다. 주문을 픽업해주세요.") // 알림 내용
        );
    }
}
