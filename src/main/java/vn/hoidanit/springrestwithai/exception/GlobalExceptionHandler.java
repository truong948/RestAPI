package vn.hoidanit.springrestwithai.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import vn.hoidanit.springrestwithai.model.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. MethodArgumentTypeMismatchException -> 400 Bad Request
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.error("MethodArgumentTypeMismatchException: ", ex);
        String message = String.format("Tham số '%s' có giá trị '%s' không đúng kiểu dữ liệu yêu cầu",
                ex.getName(), ex.getValue());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.badRequest(message));
    }

    // 2. HttpMessageNotReadableException -> 400 Bad Request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        logger.error("HttpMessageNotReadableException: ", ex);
        String message = "Dữ liệu yêu cầu (Request Body) không hợp lệ hoặc sai định dạng JSON";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.badRequest(message));
    }

    // 3. HttpRequestMethodNotSupportedException -> 405 Method Not Allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        logger.error("HttpRequestMethodNotSupportedException: ", ex);
        String message = String.format("Phương thức HTTP '%s' không được hỗ trợ cho đường dẫn này", ex.getMethod());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponse.methodNotAllowed(message));
    }

    // 4. NoHandlerFoundException -> 404 Not Found
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoHandlerFound(NoHandlerFoundException ex) {
        logger.error("NoHandlerFoundException: ", ex);
        String message = String.format("Đường dẫn '%s' không tồn tại trên hệ thống", ex.getRequestURL());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.notFound(message));
    }

    // 5. Custom Exception: ResourceNotFoundException -> 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("ResourceNotFoundException: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.notFound(ex.getMessage()));
    }

    // 6. Custom Exception: DuplicateResourceException -> 409 Conflict
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateResource(DuplicateResourceException ex) {
        logger.error("DuplicateResourceException: ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.conflict(ex.getMessage()));
    }

    // 7. Exception (catch-all) -> 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAllUncaughtException(Exception ex) {
        logger.error("Unhandled Exception: ", ex);
        String message = "Đã xảy ra lỗi nội bộ trên máy chủ, vui lòng thử lại sau";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(message));
    }
}

