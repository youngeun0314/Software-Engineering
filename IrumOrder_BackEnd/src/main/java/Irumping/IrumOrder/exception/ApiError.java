package Irumping.IrumOrder.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiError {
    // Getters and Setters
    private int statusCode;
    private String message;
    private String details;

    public ApiError(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

}
