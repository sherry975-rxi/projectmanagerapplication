package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.Project;
import project.model.User;

import java.util.List;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Integer>{

    Project findById(int id);

    List<Project> findAllByProjectManager(User user);
}





