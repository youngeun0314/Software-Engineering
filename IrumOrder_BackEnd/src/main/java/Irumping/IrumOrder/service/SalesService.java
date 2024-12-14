package Irumping.IrumOrder.service;

import Irumping.IrumOrder.repository.JpaUserRepository;
import Irumping.IrumOrder.repository.SalesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 클래스 설명: 매출 데이터를 처리하는 서비스 클래스.
 * 사용자 인증을 기반으로 특정 날짜의 총 매출 데이터를 계산한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-01
 */

@Slf4j
@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private JpaUserRepository userRepository;

    /**
     * 특정 날짜의 총 매출 데이터를 조회하는 메서드.
     * 사용자 인증(고용주 권한)을 통해 매출 데이터에 접근한다.
     *
     * @param date 조회할 날짜 (yyyy-MM-dd 형식)
     * @param userId 매출 데이터를 요청하는 사용자 ID
     * @return int 조회된 날짜의 총 매출 금액
     * @throws IllegalAccessException 사용자가 권한이 없는 경우 발생
     * @throws IllegalArgumentException 사용자 ID가 존재하지 않는 경우 발생
     */
    public int getTotalSalesByDate(LocalDate date, Integer userId) throws IllegalAccessException {
        // 사용자 모드를 조회
        String mode = userRepository.getUserMode(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // 사용자 권한 확인
        if (!"employer".equalsIgnoreCase(mode)) {
            throw new IllegalAccessException("User is not authorized to access sales data");
        }


        // 매출 데이터 조회
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        Optional<Integer> totalSales = salesRepository.findTotalSalesByDate(startOfDay, endOfDay);

        totalSales.ifPresentOrElse(
                sales -> log.info("Total sales for userId={} on date={}: {}", userId, date, sales),
                () -> log.info("No sales data found for userId={} on date={}", userId, date)
        );

        return totalSales.orElse(0);}
}

