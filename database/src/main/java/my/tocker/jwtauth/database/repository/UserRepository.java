package my.tocker.jwtauth.database.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import my.tocker.jwtauth.database.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.userName = :username")
	User findByUsername(@Param("username") String username);

}
