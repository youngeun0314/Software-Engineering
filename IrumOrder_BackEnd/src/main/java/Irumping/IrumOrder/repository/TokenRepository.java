package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.TokenEntity;
import Irumping.IrumOrder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 클래스 설명: 사용자 FCM 토큰 정보를 DB에 저장하고 조회하는 인터페이스
 * JpaRepository를 상속하여 기본적인 CRUD 기능을 제공하며,
 * 사용자 엔티티를 기반으로 토큰 엔티티를 조회하는 추가 메서드를 정의.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-05
 */

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    /**
     * 사용자 정보를 기반으로 FCM 토큰 정보를 조회하는 메소드
     *
     * @param user 사용자 엔티티
     * @return 해당 사용자와 연관된 TokenEntity 객체
     */
    TokenEntity findByUser(UserEntity user);
}