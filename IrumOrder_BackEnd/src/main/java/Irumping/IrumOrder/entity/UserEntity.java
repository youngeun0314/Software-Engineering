package Irumping.IrumOrder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String id;

    private String password;

    private String email;

    public UserEntity() {
    }

    public UserEntity(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }
}
