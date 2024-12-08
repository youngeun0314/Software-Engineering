package Irumping.IrumOrder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    @Schema(example = "1")
    private int menuId;

    @Schema(example = "Americano")
    private String menuName;

    @Schema(example = "2")
    private int quantity;

    @Schema(example = "true")
    private String useCup;

    @Schema(example = "false")
    private boolean addShot;

    @Schema(example = "true")
    private boolean addVanilla;

    @Schema(example = "false")
    private boolean addHazelnut;

    @Schema(example = "true")
    private boolean light;

    public OrderDto(int menuId, String menuName, int quantity, String useCup, boolean addShot, boolean addVanilla, boolean addHazelnut, boolean light) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.quantity = quantity;
        this.useCup = useCup;
        this.addShot = addShot;
        this.addVanilla = addVanilla;
        this.addHazelnut = addHazelnut;
        this.light = light;
    }
}
