package store.ACS.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(4401, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(4402, "User existed", HttpStatus.CONFLICT),
    USERNAME_INVALID(4403, "Invalid username", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(4404, "Invalid password", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(4405, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(4406, "Unauthenticated", HttpStatus.UNAUTHORIZED),
	ACCCESSDENIED(4407, "Unpermited", HttpStatus.FORBIDDEN),
	INVALID_PHONE(4408,"Invalid phone",HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
