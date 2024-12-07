package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.RoutineEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 인터페이스 설명: 루틴 데이터를 관리하는 JPA 리포지토리 인터페이스.
 * 데이터베이스의 `routine` 테이블과 상호작용하며, 기본 CRUD 연산과 사용자 ID 기반 조회를 제공한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-02
 */
@Repository
public interface RoutineRepository{
    /**
     * 사용자 ID를 기반으로 루틴 리스트를 조회하는 메서드.
     *
     * @param userId 루틴을 조회할 사용자 ID
     * @return List<RoutineEntity> 해당 사용자의 모든 루틴 리스트
     */

    List<RoutineEntity> findByUserId(Integer userId);

    void save(RoutineEntity routine);

    Optional<RoutineEntity> findById(Integer routineId);

    void delete(RoutineEntity routine);
}