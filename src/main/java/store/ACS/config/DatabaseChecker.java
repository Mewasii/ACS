package store.ACS.config;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import store.ACS.entity.Permission;
import store.ACS.entity.Role;
import store.ACS.entity.User;
import store.ACS.respository.RoleRepo;
import store.ACS.respository.UserRepo;

@Component
public class DatabaseChecker implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private UserRepo userRepo;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// Kiểm tra kết nối database
		try (Connection conn = dataSource.getConnection()) {
			System.out.println("✓ Connect to DataSource successfully");
		} catch (Exception e) {
			System.out.println("✗ Failed to connect to DataSource: " + e.getMessage());
			return;
		}
		try {
			List<User> users = userRepo.findAll();
			System.out.println("\nChecking users - Found " + users.size() + "users");

			if (users.isEmpty()) {
				System.out.println("No roles found in the database.");
				return;
			}
			for (User user : users) {
				System.out.println("\nChecking users - Found " + user.getUsername());
				System.out.println("==========================================");
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					System.out.println("Role Name: " + role.getName());
		                Set<Permission> permissions = role.getPermissions();
		                if (permissions != null && !permissions.isEmpty()) {
		                    System.out.println("Permissions (" + permissions.size() + "):");
		                    for (Permission permission : permissions) {
		                        System.out.println("  - " + permission.getName());
		                    }
		                }
				}
			}
			System.out.println("====================================================================================");
			List<User> user1s = userRepo.findAll();
			for (User user : user1s) {
				System.out.println("\nChecking users - Found " + user.getUsername()+ user.getRoles()); } ;
		} catch (Exception e) {
			System.out.println("Error checking users: " + e.getMessage());
			e.printStackTrace();
		}
	}

}