package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.MenuDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MenuDetailRepository extends JpaRepository<MenuDetailEntity, Integer> {

    public MenuDetailEntity save(MenuDetailEntity menuDetail);



}
