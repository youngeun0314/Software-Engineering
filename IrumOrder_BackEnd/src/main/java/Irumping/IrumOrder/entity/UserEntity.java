package Irumping.IrumOrder.entity;

<<<<<<< HEAD
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
    @Column(name = "user_id", nullable = false)
    private Long userId;

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
=======
import lombok.Getter;

@Getter
public class UserEntity {

    // 대리 키(surrogate key) 사용 고려 해볼 것
    // https://quiet-jun.tistory.com/45
    private String id;
    private String password;
    private String email;
    private String name;

    public UserEntity(String id, String password, String email, String name) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
>>>>>>> 7f7bb4bb8ba8586161cc4e94df55a95add8ad11c
    }
}
