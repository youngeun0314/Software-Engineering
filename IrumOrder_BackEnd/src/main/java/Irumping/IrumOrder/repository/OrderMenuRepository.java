package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderMenuEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 인터페이스 설명: 주문 메뉴 레포지토리
 * OrderMenuEntity와 관련된 데이터베이스 연산을 정의합니다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@Repository
public interface OrderMenuRepository {

    /**
     * OrderMenuEntity 리스트를 저장하거나 업데이트하는 메서드.
     *
     * @param orderMenuOptions 저장 또는 업데이트할 OrderMenuEntity 리스트
     */
    void saveAll(List<OrderMenuEntity> orderMenuOptions);
}
