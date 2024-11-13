package Irumping.IrumOrder.Entity;

public enum OrderStatus {

    // 예약대기,주문접수,상품준비완료,완료
    // WAITING, ACCEPTED, PREPARED, COMPLETED;

    // 직원 view : 조리시작, 조리완료, 픽업완료
    COOKING_START, COOKING_END, PICKUP_END,
    // 고객 view : 조리 중, 상품 준비완료, 완료
    COOKING, PREPARED, COMPLETED;
}
