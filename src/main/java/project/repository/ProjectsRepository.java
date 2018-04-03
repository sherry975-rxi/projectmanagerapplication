package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.Project;
import project.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Integer>{

    Optional<Project> findById(Integer id);

    List<Project> findAllByProjectManager(User user);
}





