package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 클래스 설명: FCM 토큰 정보를 저장하는 엔티티
 * 사용자(UserEntity)와 연관된 FCM 토큰 정보를 담고 있음
 *
 * 주요 필드:
 * - tokenId: 고유 ID
 * - user: FCM 토큰을 소유한 사용자(UserEntity)
 * - fcmToken: FCM 푸시 알림에 사용되는 토큰
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-05
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    private Integer tokenId;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "token", nullable = false)
    private String fcmToken;

    public TokenEntity(UserEntity user, String fcmToken) {
        this.user = user;
        this.fcmToken = fcmToken;
    }
}
