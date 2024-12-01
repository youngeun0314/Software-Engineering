package Irumping.IrumOrder.exception;

import Irumping.IrumOrder.exception.CustomExceptions.InvalidInputException;
import Irumping.IrumOrder.exception.CustomExceptions.InvalidRoutineException;
import Irumping.IrumOrder.exception.CustomExceptions.UserIdMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLNonTransientConnectionException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ApiError> buildResponseEntity(HttpStatus status, String message, String details) {
        ApiError error = new ApiError(status.value(), message, details);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserIdMismatchException.class)
    public ResponseEntity<ApiError> handleUserIdMismatchException(UserIdMismatchException ex) {
        logger.error("UserIdMismatchException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), "User ID mismatch");
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiError> handleInvalidInputException(InvalidInputException ex) {
        logger.error("InvalidInputException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), "Invalid input provided");
    }

    @ExceptionHandler(InvalidRoutineException.class)
    public ResponseEntity<ApiError> handleInvalidRoutineException(InvalidRoutineException ex) {
        logger.error("InvalidRoutineException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), "Invalid routine operation");
    }

    @ExceptionHandler({CannotCreateTransactionException.class, DataAccessException.class, SQLNonTransientConnectionException.class})
    public ResponseEntity<ApiError> handleDatabaseConnectionException(Exception ex) {
        logger.error("Database connection exception: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, "Database connection error", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        logger.error("Unhandled exception: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex.getMessage());
    }
}
