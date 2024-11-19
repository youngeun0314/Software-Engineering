package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.dto.SalesResponse;
import Irumping.IrumOrder.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SalesController {
    @Autowired
    private SalesService salesService;

    @GetMapping("/api/sales?date=yesterday")
    public SalesResponse getYesterdaySales() {
        int totalSales = salesService.getYesterdayTotalSales();
        return new SalesResponse(totalSales);
    }
}
