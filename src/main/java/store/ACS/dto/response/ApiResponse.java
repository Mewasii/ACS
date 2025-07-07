package store.ACS.dto.response;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "success", "message", "timestamp", "status", "result" }) // Sắp xếp thứ tự JSON trả về
public class ApiResponse<T> {
	Boolean success;
	String message;
	T result;
	LocalDateTime timestamp;
	int status;

	public ApiResponse() {
		this.timestamp = LocalDateTime.now();
	}

	public ApiResponse(Boolean success, String message, T result, int status) {
		this.success = success;
		this.message = message;
		this.timestamp = LocalDateTime.now();
		this.status = status;
		this.result = result;
	}
}
