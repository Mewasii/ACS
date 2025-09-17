package store.ACS.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import store.ACS.dto.response.ApiResponse;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MUST_ATTRIBUTE = "must";
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";
    private static final String SIZE_ATTRIBUTE = "size";
    private static final String VALUE_ATTRIBUTE = "value";
    
    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ApiResponse<Object>> handleNotFound(Exception ex) {
        return buildResponse(ErrorCode.USER_NOT_EXISTED, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        ErrorCode errorCode = determineErrorCode(fieldError, ex.getMessage());
        
        String message = errorCode.getMessage();
        
        // Xử lý mapping attributes từ constraint violations
        if (!ex.getBindingResult().getAllErrors().isEmpty()) {
            try {
                ConstraintViolation<?> constraintViolation = ex.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
                Map<String, Object> attributes = constraintViolation.getConstraintDescriptor().getAttributes();
                message = mapAttributes(message, attributes);
                log.info("Constraint attributes: {}", attributes);
            } catch (Exception e) {
                log.warn("Could not unwrap ConstraintViolation: {}", e.getMessage());
            }
        }
        
        // Thêm thông tin chi tiết từ field error nếu có
        if (fieldError != null && fieldError.getDefaultMessage() != null) {
            message = message + ": " + fieldError.getDefaultMessage();
        }
        
        return buildResponse(errorCode, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        String message = errorCode.getMessage();
        
        if (!violations.isEmpty()) {
            ConstraintViolation<?> violation = violations.iterator().next();
            Map<String, Object> attributes = violation.getConstraintDescriptor().getAttributes();
            message = mapAttributes(message, attributes);
            log.info("Constraint violation attributes: {}", attributes);
        }
        
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
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Object>> handleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        String message = errorCode.getMessage();

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
            if ("phone".equals(field) && errorMessage != null && errorMessage.contains("Phone error")) {
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
    
    private String mapAttributes(String message, Map<String, Object> attributes) {
        String result = message;
        
        // Replace common constraint attributes
        if (attributes.containsKey(MUST_ATTRIBUTE)) {
            String mustValue = String.valueOf(attributes.get(MUST_ATTRIBUTE));
            result = result.replace("{" + MUST_ATTRIBUTE + "}", mustValue);
        }
        
        if (attributes.containsKey(MIN_ATTRIBUTE)) {
            String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
            result = result.replace("{" + MIN_ATTRIBUTE + "}", minValue);
        }
        
        if (attributes.containsKey(MAX_ATTRIBUTE)) {
            String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));
            result = result.replace("{" + MAX_ATTRIBUTE + "}", maxValue);
        }
        
        if (attributes.containsKey(SIZE_ATTRIBUTE)) {
            String sizeValue = String.valueOf(attributes.get(SIZE_ATTRIBUTE));
            result = result.replace("{" + SIZE_ATTRIBUTE + "}", sizeValue);
        }
        
        if (attributes.containsKey(VALUE_ATTRIBUTE)) {
            String value = String.valueOf(attributes.get(VALUE_ATTRIBUTE));
            result = result.replace("{" + VALUE_ATTRIBUTE + "}", value);
        }
        
        // Replace any other placeholders dynamically
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            if (result.contains(placeholder)) {
                result = result.replace(placeholder, String.valueOf(entry.getValue()));
            }
        }
        
        return result;
    }
}