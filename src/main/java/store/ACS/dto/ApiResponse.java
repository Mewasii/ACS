package store.ACS.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "success", "message", "timestamp", "status", "result" }) // Sắp xếp thứ tự JSON trả về
public class ApiResponse<T> {
	private Boolean success;
	private String message;
	private T result;
	private LocalDateTime timestamp;
	private int status;

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

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
