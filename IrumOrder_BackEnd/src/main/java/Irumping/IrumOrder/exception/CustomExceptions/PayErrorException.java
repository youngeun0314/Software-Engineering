package Irumping.IrumOrder.exception.CustomExceptions;

public class PayErrorException extends RuntimeException{
    public PayErrorException(String message){
        super(message);
    }
}
