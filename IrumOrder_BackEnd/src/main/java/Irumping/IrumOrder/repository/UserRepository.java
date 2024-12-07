package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.UserEntity;

import java.util.Optional;

/**
 * 클래스 설명: 사용자 정보를 DB에 저장하고 조회하는 인터페이스
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-05
 */
public interface UserRepository {

    public String getPassword(String id);

    public void save(String id, String password, String email);

    boolean isExist(String id);

    Optional<UserEntity> findById(String id);

    public Optional<UserEntity> findByUserId(Integer userId);
}
