package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

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
