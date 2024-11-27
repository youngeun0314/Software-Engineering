package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    private Long tokenId;

    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "fcmToken", nullable = false)
    private String fcmToken;

    public TokenEntity(UserEntity user, String fcmToken) {
        this.user = user;
        this.fcmToken = fcmToken;
    }
}
