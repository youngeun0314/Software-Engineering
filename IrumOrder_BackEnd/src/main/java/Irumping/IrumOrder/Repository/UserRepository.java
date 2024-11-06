package Irumping.IrumOrder.Repository;

public interface UserRepository {

    public String getPassword(String id);

    public void save(String id, String password, String email);
}
