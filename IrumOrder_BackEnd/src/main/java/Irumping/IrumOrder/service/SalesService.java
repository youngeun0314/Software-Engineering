package Irumping.IrumOrder.service;

import Irumping.IrumOrder.repository.JpaUserRepository;
import Irumping.IrumOrder.repository.SalesRepository;
import Irumping.IrumOrder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private JpaUserRepository userRepository;

    public int getTotalSalesByDate(LocalDate date, long userId) throws IllegalAccessException {
        String mode = userRepository.getUserMode(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (!"employer".equalsIgnoreCase(mode)) {
            throw new IllegalAccessException("User is not authorized to access sales data");
        }
        // 어제의 시작과 끝 시각 계산
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        // 전날 총 매출 계산
        return salesRepository.findTotalSalesByDate(startOfDay, endOfDay).orElse(0);
    }
}

