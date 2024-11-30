package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.dto.SalesResponse;
import Irumping.IrumOrder.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
public class SalesController {
    @Autowired
    private SalesService salesService;

    @GetMapping("/api/sales")
    public ResponseEntity<SalesResponse> getYesterdaySales(@RequestParam LocalDate date, @RequestParam long userId) throws IllegalAccessException {
        int totalSales = salesService.getTotalSalesByDate(date, userId);
        return ResponseEntity.status(HttpStatus.OK).body(new SalesResponse(totalSales));
    }
}
