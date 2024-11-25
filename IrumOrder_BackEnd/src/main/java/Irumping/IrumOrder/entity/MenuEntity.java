package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static jakarta.persistence.FetchType.*;

@Getter
@Setter
@Entity
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    private Long price;

    @ManyToOne(fetch = LAZY)
    private CategoryEntity category;

//    @OneToOne(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
//    private MenuDetailEntity menuDetail;

    public MenuEntity() {
    }

    public MenuEntity(String name, Long price, CategoryEntity category) {
        this.name = name;
        this.price = price;
        this.category = category;
//        this.menuDetail = menuDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuEntity that = (MenuEntity) o;
        return menuId == that.menuId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }
}
