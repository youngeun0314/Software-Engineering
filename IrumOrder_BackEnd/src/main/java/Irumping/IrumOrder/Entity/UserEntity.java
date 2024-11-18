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
    private Long id;

    private String userId;

    private String password;

    private String email;

    public UserEntity() {
    }

    public UserEntity(String userId, String password, String email) {
        this.userId = userId;
        this.password = password;
        this.email = email;
    }
}
