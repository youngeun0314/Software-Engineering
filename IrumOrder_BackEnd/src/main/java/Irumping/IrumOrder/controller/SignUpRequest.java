package Irumping.IrumOrder.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    private String userId;
    private String password;
    private String email;

    public SignUpRequest(String userId, String password, String email) {
        this.userId = userId;
        this.password = password;
        this.email = email;
    }
}
