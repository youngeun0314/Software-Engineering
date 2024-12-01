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

/**
 * 클래스 설명: 전역적으로 발생하는 예외를 처리하는 핸들러 클래스.
 * 모든 컨트롤러에서 발생하는 예외를 포착하여 사용자 친화적인 에러 메시지를 반환한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-01
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 에러 응답을 생성하는 헬퍼 메서드.
     *
     * @param status  HTTP 상태 코드
     * @param message 클라이언트에 표시할 에러 메시지
     * @param details 에러 상세 정보
     * @return ResponseEntity<ApiError> 생성된 에러 응답 객체
     */
    private ResponseEntity<ApiError> buildResponseEntity(HttpStatus status, String message, String details) {
        ApiError error = new ApiError(status.value(), message, details);
        return ResponseEntity.status(status).body(error);
    }

    /**
     * 사용자 ID 불일치 예외를 처리하는 메서드.
     *
     * @param ex UserIdMismatchException 예외 객체
     * @return ResponseEntity<ApiError> 에러 응답 객체
     */
    @ExceptionHandler(UserIdMismatchException.class)
    public ResponseEntity<ApiError> handleUserIdMismatchException(UserIdMismatchException ex) {
        logger.error("UserIdMismatchException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), "User ID mismatch");
    }

    /**
     * 잘못된 입력값 예외를 처리하는 메서드.
     *
     * @param ex InvalidInputException 예외 객체
     * @return ResponseEntity<ApiError> 에러 응답 객체
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiError> handleInvalidInputException(InvalidInputException ex) {
        logger.error("InvalidInputException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), "Invalid input provided");
    }

    /**
     * 잘못된 루틴 예외를 처리하는 메서드.
     *
     * @param ex InvalidRoutineException 예외 객체
     * @return ResponseEntity<ApiError> 에러 응답 객체
     */
    @ExceptionHandler(InvalidRoutineException.class)
    public ResponseEntity<ApiError> handleInvalidRoutineException(InvalidRoutineException ex) {
        logger.error("InvalidRoutineException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), "Invalid routine operation");
    }

    /**
     * 데이터베이스 연결 관련 예외를 처리하는 메서드.
     *
     * @param ex CannotCreateTransactionException, DataAccessException, SQLNonTransientConnectionException 예외 객체
     * @return ResponseEntity<ApiError> 에러 응답 객체
     */
    @ExceptionHandler({CannotCreateTransactionException.class, DataAccessException.class, SQLNonTransientConnectionException.class})
    public ResponseEntity<ApiError> handleDatabaseConnectionException(Exception ex) {
        logger.error("Database connection exception: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, "Database connection error", ex.getMessage());
    }

    /**
     * 예상치 못한 일반적인 예외를 처리하는 메서드.
     *
     * @param ex Exception 예외 객체
     * @return ResponseEntity<ApiError> 에러 응답 객체
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        logger.error("Unhandled exception: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex.getMessage());
    }
}
