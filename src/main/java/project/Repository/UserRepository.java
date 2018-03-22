package project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.model.Profile;
import project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	List<User> findAllByUserProfile(Profile profile);

	void deleteByEmail(String email);

	Boolean existsByEmail(String userEmail);
}
