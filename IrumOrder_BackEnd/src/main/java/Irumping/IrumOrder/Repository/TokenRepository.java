package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    TokenEntity findByUserId(Integer userId);
}