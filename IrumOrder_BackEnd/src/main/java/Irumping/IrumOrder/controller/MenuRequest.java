package Irumping.IrumOrder.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 클래스 설명: 메뉴 생성 요청 정보를 담는 클래스
 * 사용자가 입력한 메뉴 이름, 가격, 카테고리 아이디를 담는다.
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
@Getter
@Setter
@RequiredArgsConstructor
public class MenuRequest {

    private String name;
    private Integer price;
    private Integer categoryId;

}
