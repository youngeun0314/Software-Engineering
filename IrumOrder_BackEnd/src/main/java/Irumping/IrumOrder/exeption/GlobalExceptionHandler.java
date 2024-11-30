package Irumping.IrumOrder.exeption;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLNonTransientConnectionException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserIdMismatchException.class)
    public ResponseEntity<String> handleUserIdMismatchException(UserIdMismatchException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400 에러 반환
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400 Bad Request
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidRoutineExceiption.class)
    public ResponseEntity<String> handleInvalidRoutineExceiption(InvalidRoutineExceiption ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400 Bad Request
                .body(ex.getMessage());
    }

    @ExceptionHandler({CannotCreateTransactionException.class, DataAccessException.class, SQLNonTransientConnectionException.class})
    public ResponseEntity<String> handleDatabaseConnectionException(Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("DB connection failed.");
    }

    @ExceptionHandler(Exception.class) // 다른 모든 예외 처리
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }



}