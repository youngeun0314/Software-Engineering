package Irumping.IrumOrder.Controller;

public class SignUpRequest {

    private String id;
    private String password;
    private String email;

    public SignUpRequest(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
