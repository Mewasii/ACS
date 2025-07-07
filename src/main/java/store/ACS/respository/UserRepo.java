package store.ACS.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.ACS.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User ,Long> {
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	User findByUsername(String username);
}