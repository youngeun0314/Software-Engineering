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

}
