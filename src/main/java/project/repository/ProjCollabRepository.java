package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjCollabRepository extends JpaRepository<ProjectCollaborator, Integer>{

	/**
	 * Finds all projectCollaborators from a certain user
	 *
	 * @param user user to search
	 *
	 * @return a List of projectCollaborators from a certain user
	 */
	List<ProjectCollaborator> findAllByCollaborator(User user);


	/**
	 * Finds all projectCollaboators from a certain project
	 *
	 * @param project project to search
	 *
	 * @return List of Project Collaborators from a certain project
	 */
	List<ProjectCollaborator> findAllByProject(Project project);

	/**
	 * Finds all projectCollaboators from a certain project and user
	 *
	 * @param project project to search
	 *
	 * @param user user to search
	 *
	 * @return List of Project Collaborators from a certain project and user
	 */
	List<ProjectCollaborator> findAllByProjectAndCollaborator(Project project, User user);

	/**
	 * Finds a collaborator by its ProjCollabId
	 *
	 * @param id Id to search for
	 *
	 * @return ProjectCollaborator
	 */
	Optional<ProjectCollaborator> findByProjectCollaboratorId(long id);
	
}
