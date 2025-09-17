package store.ACS.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.ACS.entity.InvalidatedToken;
import store.ACS.entity.Permission;

@Repository
public interface InvalidTokenRepo extends JpaRepository<InvalidatedToken ,String> {

}