package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.MenuDetailEntity;

/**
 * 인터페이스 설명: 메뉴 디테일 레포지토리
 * MenuDetailEntity와 관련된 데이터베이스 연산을 정의합니다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-09
 */
public interface MenuDetailRepository {

    /**
     * 메뉴 디테일 정보를 저장하거나 업데이트하는 메서드.
     *
     * @param menuDetailEntity 저장하거나 업데이트할 MenuDetailEntity 객체
     */
    void save(MenuDetailEntity menuDetailEntity);
    MenuDetailEntity findById(int menuDetailid);
}
