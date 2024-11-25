package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.MenuDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDetailRepository extends JpaRepository<MenuDetailEntity, Integer> {

    public MenuDetailEntity save(MenuDetailEntity menuDetail);



}
