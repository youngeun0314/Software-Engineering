package Irumping.IrumOrder.repository;

public interface UserRepository {

    public String getPassword(String id);

    public void save(String id, String password, String email);

    boolean isExist(String id);
}
