package Irumping.IrumOrder.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class MenuRequest {

    private String name;
    private Long price;
    private Long categoryId;

    private String useCup;
    private boolean addShot;
    private boolean addVanilla;
    private boolean addHazelnut;
    private boolean light;

}
