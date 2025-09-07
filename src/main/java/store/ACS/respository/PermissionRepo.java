package store.ACS.respository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.ACS.entity.Permission;
@Repository
public interface PermissionRepo extends JpaRepository<Permission ,String> {

}
