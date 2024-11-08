package Irumping.IrumOrder.entity;

import lombok.Getter;

@Getter
public class UserEntity {

    private String id;
    private String password;
    private String email;

    public UserEntity(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }
}
