package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 클래스 설명: 사용자 정보를 담는 엔티티
 * 사용자의 아이디, 비밀번호, 이메일 정보를 담고 있음
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
@Setter
@Getter
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    private String id;

    private String password;

    private String email;

    private String mode;

    public UserEntity() {
    }

    public UserEntity(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.mode = "일반";
    }
}
