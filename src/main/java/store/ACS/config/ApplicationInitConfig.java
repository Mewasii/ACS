package store.ACS.config;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import store.ACS.entity.User;
import store.ACS.enums.Role;
import store.ACS.respository.UserRepo;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Bean
	ApplicationRunner applicationRunner(UserRepo userRepo) {
		return args -> {
			if (userRepo.findByUsername("admin").isEmpty()) {
				var roles = new HashSet<String>();
				roles.add(Role.ADMIN.name());
				User user = User.builder().username("admin")
						//.roles(roles)
						.password(passwordEncoder.encode("admin"))
						.build();
				userRepo.save(user);
				log.warn("admin have been created with default password");
			}
		};
	}
}
