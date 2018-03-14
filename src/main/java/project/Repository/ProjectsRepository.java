package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Project;
import project.model.User;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Project, Integer>{

    Project findById(long id);

    List<Project> findByProjectStatus(int status);

    List<Project> findAllByProjectManager(User user);
}





