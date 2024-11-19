package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;

    public int getYesterdayTotalSales() {
        // 어제의 시작과 끝 시각 계산
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime startOfDay = yesterday.atStartOfDay();
        LocalDateTime endOfDay = yesterday.atTime(LocalTime.MAX);

        // 전날 총 매출 계산
        return salesRepository.findTotalSalesByDate(startOfDay, endOfDay).orElse(0);
    }
}
