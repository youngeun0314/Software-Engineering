package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 클래스 설명: 메뉴 정보를 담는 엔티티.
 * 메뉴의 ID, 이름, 가격, 그리고 해당 메뉴의 카테고리 정보를 관리한다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@Setter
@Getter
@Entity
@Table(name = "menu")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", nullable = false)
    private Integer menuId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private CategoryEntity category;

    /**
     * 기본 생성자.
     */
    public MenuEntity() {}

    /**
     * 메뉴 정보를 초기화하는 생성자.
     *
     * @param name     메뉴 이름
     * @param price    메뉴 가격
     * @param category 메뉴가 속한 카테고리
     */
    public MenuEntity(String name, Integer price, CategoryEntity category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    /**
     * 두 객체가 같은지 비교하는 메서드.
     *
     * @param o 비교할 객체
     * @return 동일 여부
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuEntity that = (MenuEntity) o;
        return Objects.equals(menuId, that.menuId);
    }

    /**
     * 객체의 해시코드를 반환하는 메서드.
     *
     * @return 객체의 해시코드
     */
    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }
}
