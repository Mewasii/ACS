package store.ACS.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import store.ACS.validator.PhoneConstraint;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "userid")
	UUID userid;

	@Column(name = "username", nullable = false, unique = true, columnDefinition = "nvarchar(100)")
	String username;

	@Column(name = "password", nullable = false, columnDefinition = "nvarchar(100)")
	String password;

	@Column(name = "fullname", columnDefinition = "nvarchar(100)")
	private String fullname;

	@Column(name = "email", unique = true, columnDefinition = "nvarchar(100)")
	String email;
	
	@Column(name = "phone", columnDefinition = "nvarchar(20)")
	String phone;

	@ManyToMany
	Set<Role> roles;

	@Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
	Boolean active;

}