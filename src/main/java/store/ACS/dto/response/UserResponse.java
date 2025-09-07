package store.ACS.dto.response;

import java.util.Set;

import javax.management.relation.Role;

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
public class UserResponse {
	String password;
	String fullname;
	String email;
	String phone;
	Set<RoleResponse> roles;
	Boolean active;
}
