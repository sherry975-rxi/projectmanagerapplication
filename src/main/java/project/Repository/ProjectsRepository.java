package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Project;

public interface ProjectsRepository extends JpaRepository<Project, Integer>{

    Project findById(long id);
}
