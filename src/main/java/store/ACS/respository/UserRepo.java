package store.ACS.respository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.ACS.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User ,UUID> {
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	Optional<User> findByUsername(String username);
}