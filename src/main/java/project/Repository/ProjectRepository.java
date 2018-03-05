package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
}
