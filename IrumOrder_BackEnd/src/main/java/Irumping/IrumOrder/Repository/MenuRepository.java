package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

    // 이름으로 메뉴 검색
    Optional<MenuEntity> findByName(String name);

    // 카테고리에 따라 검색
    List<MenuEntity> findByCategory(String category);
}
