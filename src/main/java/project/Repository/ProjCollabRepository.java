package project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.ProjectCollaborator;
import project.model.User;

public interface ProjCollabRepository extends JpaRepository<ProjectCollaborator, Integer>{

	/**
	 * Finds all projectCollaborators from a certain user
	 *
	 * @param user user to search
	 *
	 * @return a List of projectCollaborators from a certain user
	 */
	List<ProjectCollaborator> findAllByCollaborator(User user);
	
}
