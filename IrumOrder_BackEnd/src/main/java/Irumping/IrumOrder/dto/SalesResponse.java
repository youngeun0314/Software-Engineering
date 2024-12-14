package Irumping.IrumOrder.dto;

import lombok.Getter;

@Getter
public class SalesResponse {
    private final int totalSales;
    public SalesResponse(int TotalSales) {
        this.totalSales = TotalSales;
    }

}