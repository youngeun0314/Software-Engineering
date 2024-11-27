package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer userId;


    private String fcmToken;

    public TokenEntity(Integer userId, String fcmToken){
        this.userId = userId;
        this.fcmToken = fcmToken;
    }
}
