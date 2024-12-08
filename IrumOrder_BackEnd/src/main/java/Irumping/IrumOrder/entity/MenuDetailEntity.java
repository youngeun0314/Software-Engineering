package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 클래스 설명: 메뉴 상세 정보를 담는 엔티티.
 * 각 메뉴에 대한 상세 옵션(컵 사용 여부, 샷 추가, 바닐라 추가, 헤이즐넛 추가, 라이트 옵션 등)을 관리한다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@Getter
@Setter
@Entity
@Table(name = "menudetails")
public class MenuDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_detail_id", nullable = false)
    private Integer menuDetailId;

    @Column(name = "use_cup", nullable = false)
    private String useCup;

    @Column(name = "add_shot", nullable = false)
    private boolean addShot;

    @Column(name = "add_vanilla", nullable = false)
    private boolean addVanilla;

    @Column(name = "add_hazelnut", nullable = false)
    private boolean addHazelnut;

    @Column(name = "light", nullable = false)
    private boolean light;

    /**
     * 기본 생성자.
     */
    public MenuDetailEntity() {
    }

    /**
     * 메뉴 상세 정보를 초기화하는 생성자.
     *
     * @param useCup       컵 사용 여부
     * @param addShot      샷 추가 여부
     * @param addVanilla   바닐라 추가 여부
     * @param addHazelnut  헤이즐넛 추가 여부
     * @param light        라이트 옵션 여부
     */
    public MenuDetailEntity(String useCup, boolean addShot, boolean addVanilla, boolean addHazelnut, boolean light) {
        this.useCup = useCup;
        this.addShot = addShot;
        this.addVanilla = addVanilla;
        this.addHazelnut = addHazelnut;
        this.light = light;
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
        MenuDetailEntity that = (MenuDetailEntity) o;
        return Objects.equals(menuDetailId, that.menuDetailId);
    }

    /**
     * 객체의 해시코드를 반환하는 메서드.
     *
     * @return 객체의 해시코드
     */
    @Override
    public int hashCode() {
        return Objects.hash(menuDetailId);
    }
}
