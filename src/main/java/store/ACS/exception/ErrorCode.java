package store.ACS.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_PHONE(4408, "Phone must be {must} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(4409, "Invalid password", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(4410, "Invalid username", HttpStatus.BAD_REQUEST),
    USER_EXISTED(4401, "User existed", HttpStatus.CONFLICT),
    USER_NOT_EXISTED(4402, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(4403, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    ACCCESSDENIED(4404, "Access denied", HttpStatus.FORBIDDEN),
    INVALID_KEY(4405, "Invalid key", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(4500, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN_INVALID(4406, "Token signature is invalid", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(4407, "Token has expired", HttpStatus.UNAUTHORIZED),
    TOKEN_BLACKLISTED(4411, "Token has been logged out or blacklisted", HttpStatus.UNAUTHORIZED);
	

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
