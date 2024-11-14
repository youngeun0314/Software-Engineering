package Irumping.IrumOrder.Entity;

public enum OrderStatus {

    // 직원 view : 조리시작, 조리완료, 픽업완료
    COOKING_START, COOKING_END, PICKUP_END,
    // 고객 view : 조리 중, 상품 준비완료, 완료
    COOKING, PREPARED, COMPLETED;
}
