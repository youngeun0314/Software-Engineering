package Irumping.IrumOrder.exception.CustomExceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}