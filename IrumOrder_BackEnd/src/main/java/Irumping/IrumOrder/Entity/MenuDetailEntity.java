package Irumping.IrumOrder.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class MenuDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuDetailId;
    @Column(nullable = false, length = 50)
    private String useCup;
    private boolean addShot = false;
    private boolean addVanila = false;
    private boolean addHazelnut = false;
    private boolean light = false;
    @OneToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "menuId")
    private MenuEntity menu;
    // Constructors
    public MenuDetailEntity() {}
    public MenuDetailEntity(String useCup, boolean addShot, boolean addVanila, boolean addHazelnut, boolean light) {
        this.useCup = useCup;
        this.addShot = addShot;
        this.addVanila = addVanila;
        this.addHazelnut = addHazelnut;
        this.light = light;
    }
    // Equals and HashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDetailEntity that = (MenuDetailEntity) o;
        return menuDetailId == that.menuDetailId;
    }
    @Override
    public int hashCode() {
        return Objects.hash(menuDetailId);
    }
}
