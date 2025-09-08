package store.ACS.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import store.ACS.dto.response.ApiResponse;

import java.time.LocalDateTime;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ApiResponse<Object>> handleNotFound(Exception ex) {
        return buildResponse(ErrorCode.USER_NOT_EXISTED, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        ErrorCode errorCode = determineErrorCode(fieldError, ex.getMessage());
        String message = Optional.ofNullable(fieldError)
                .map(error -> errorCode.getMessage() + ": " + error.getDefaultMessage())
                .orElse(errorCode.getMessage());
        return buildResponse(errorCode, message);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntime(RuntimeException ex) {
        ErrorCode errorCode = determineErrorCode(null, ex.getMessage());
        String message = Optional.ofNullable(ex.getMessage())
                .map(msg -> errorCode.getMessage() + ": " + msg)
                .orElse(errorCode.getMessage());
        return buildResponse(errorCode, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception ex) {
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        String message = Optional.ofNullable(ex.getMessage())
                .map(msg -> errorCode.getMessage() + ": " + msg)
                .orElse(errorCode.getMessage());
        return buildResponse(errorCode, message);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleDeniedException(Exception ex) {
        ErrorCode errorCode = ErrorCode.ACCCESSDENIED;
        String message = Optional.ofNullable(ex.getMessage())
                .map(msg -> errorCode.getMessage() + ": " + msg)
                .orElse(errorCode.getMessage());
        return buildResponse(errorCode, message);
    }

    private ResponseEntity<ApiResponse<Object>> buildResponse(ErrorCode errorCode, String customMessage) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .code(errorCode.getCode())
                .message(customMessage != null ? customMessage : errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .status(errorCode.getStatusCode().value())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }
    

    private ErrorCode determineErrorCode(FieldError fieldError, String message) {
        if (fieldError != null) {
            String field = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            if ("username".equals(field) && errorMessage != null && errorMessage.contains("3 characters")) {
                return ErrorCode.USERNAME_INVALID;
            }
            if ("password".equals(field) && errorMessage != null && errorMessage.contains("8 characters")) {
                return ErrorCode.INVALID_PASSWORD;
            }
            if ("phone".equals(field) && errorMessage != null && errorMessage.contains("10 characters")) {
                return ErrorCode.INVALID_PHONE;
            }
            return ErrorCode.INVALID_KEY;
        }
        if (message != null) {
            if (message.contains("User existed")) return ErrorCode.USER_EXISTED;
            if (message.contains("Unauthenticated")) return ErrorCode.UNAUTHENTICATED;
            if (message.contains("User not existed")) return ErrorCode.USER_NOT_EXISTED;

        }
        return ErrorCode.UNCATEGORIZED_EXCEPTION;
    }
}