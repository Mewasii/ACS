package store.ACS.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreRequest {

	@NotBlank(message = "Username is required")
	@Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
	String username;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters")
	String password;

	@NotBlank(message = "Fullname is required")
	@Size(max = 50, message = "Fullname must be at most 50 characters")
	String fullname;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	String email;

	@NotBlank(message = "Phone is required")
	@Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{9,11}$", message = "Invalid phone number format")
	String phone;
	String role;
	Boolean active;

}
