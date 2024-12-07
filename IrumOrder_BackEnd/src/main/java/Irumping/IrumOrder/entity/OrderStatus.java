package Irumping.IrumOrder.entity;

public enum OrderStatus {
    // 직원 view : 신규주문, 예약대기, 주문접수, 상품준비완료, 완료
    NEW_ORDER, WAITING, ACCEPTED_ORDER, READY_FOR_PICKUP, COMPLETED,

    // 직원 상세 view : 승인, 조리시작, 조리완료, 픽업완료
    ACCEPTED, COOKING_START, COOKING_END, PICKUP_END;
}
