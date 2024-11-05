package Irumping.IrumOrder.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayOrderForm {
    String name;
    int totalPrice;

    public PayOrderForm(String name, int totalPrice){
        this.name=name;
        this.totalPrice=totalPrice;
    }
}
