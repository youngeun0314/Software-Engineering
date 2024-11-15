package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping("/api/yesterday-sales")
    public Map<String, Integer> getYesterdaySales() {
        int totalSales = salesService.getYesterdayTotalSales();
        return Map.of("yesterdayTotalSales", totalSales);
    }
}
