package Irumping.IrumOrder.repository;

public interface UserRepository {

    public String getPassword(String userId);

    public void save(String userId, String password, String email);

    boolean isExist(String userId);
}
