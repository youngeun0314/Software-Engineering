package Irumping.IrumOrder.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    private String id;
    private String password;
    private String email;

    public SignUpRequest(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }
}
