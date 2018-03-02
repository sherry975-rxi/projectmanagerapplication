package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.ProjectCollaborator;
import project.model.User;

public interface ProjCollabRepository extends JpaRepository<ProjectCollaborator, Integer>{
}
