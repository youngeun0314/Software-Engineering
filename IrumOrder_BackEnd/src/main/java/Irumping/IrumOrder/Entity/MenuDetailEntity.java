package Irumping.IrumOrder.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "menudetails")
public class MenuDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_detail_id", nullable = false)
    private Integer menuDetailId;

    @Column(name = "use_cup", nullable = false)
    private String useCup;

    @Column(name = "add_shot", nullable = false)
    private boolean addShot;

    @Column(name = "add_vanilla", nullable = false)
    private boolean addVanilla;

    @Column(name = "add_hazelnut", nullable = false)
    private boolean addHazelnut;

    @Column(name = "light", nullable = false)
    private boolean light;

    // Constructors
    public MenuDetailEntity() {
    }

    public MenuDetailEntity(String useCup, boolean addShot, boolean addVanilla, boolean addHazelnut, boolean light) {
        this.useCup = useCup;
        this.addShot = addShot;
        this.addVanilla = addVanilla;
        this.addHazelnut = addHazelnut;
        this.light = light;
    }

    // Equals and HashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDetailEntity that = (MenuDetailEntity) o;
        return Objects.equals(menuDetailId, that.menuDetailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuDetailId);
    }
}
