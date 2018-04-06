package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.Profile;
import project.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	User findById(int id);

	List<User> findAllByUserProfile(Profile profile);

	void deleteByEmail(String email);

	Boolean existsByEmail(String userEmail);

}


