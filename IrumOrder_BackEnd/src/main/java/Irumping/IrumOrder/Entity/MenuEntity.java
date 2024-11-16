package Irumping.IrumOrder.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

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
    public MenuEntity() {
    }
    public MenuEntity(String name, Long price, CategoryEntity category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuEntity that = (MenuEntity) o;
        return Objects.equals(menuId, that.menuId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }
}
