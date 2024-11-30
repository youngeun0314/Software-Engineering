package Irumping.IrumOrder.exception.CustomExceptions;

public class UserIdMismatchException extends RuntimeException {
    public UserIdMismatchException(String message) {
        super(message);
    }
}
