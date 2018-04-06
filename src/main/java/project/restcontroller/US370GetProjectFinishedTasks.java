package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.Project;
import project.model.Task;
import project.services.ProjectService;
import project.services.TaskService;

import java.util.List;


@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class US370GetProjectFinishedTasks {

    private final ProjectService projectsService;

    private final TaskService taskService;

    @Autowired
    public US370GetProjectFinishedTasks(ProjectService projServ, TaskService taskServ) {
        this.projectsService = projServ;
        this.taskService = taskServ;
    }

    @RequestMapping(value = "/finished", method = RequestMethod.GET)
    public ResponseEntity<?> getFinishedTasks(@PathVariable int projectId) {

        Project project = this.projectsService.getProjectById(projectId);
        List<Task> tasks =  this.taskService.getProjectFinishedTasks(project);

        return ResponseEntity.ok().body(tasks);
    }

}
