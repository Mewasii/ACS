package store.ACS.dto.response;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "success","code","message", "timestamp", "status", "result" }) // Sắp xếp thứ tự JSON trả về
public class ApiResponse<T> {
	Boolean success;
	int code;
	String message;
	T result;
	LocalDateTime timestamp;
	int status;
}
