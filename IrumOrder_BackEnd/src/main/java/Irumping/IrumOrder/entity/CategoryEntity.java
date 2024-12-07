package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 클래스 설명: 카테고리 엔티티
 * 카테고리 아이디, 카테고리 이름을 담는다.
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    private String name;

    public CategoryEntity() {
    }
}
