package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.Task;
import project.services.ProjectService;
import project.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectID}/tasks")
public class US372GetStartedNotFinishedProjectTasksRestController {

    private final ProjectService projectsService;

    private final TaskService taskService;


    @Autowired
    public US372GetStartedNotFinishedProjectTasksRestController(ProjectService projServ, TaskService taskServ) {


        this.projectsService = projServ;
        this.taskService = taskServ;
    }

    @RequestMapping(value = "unfinished", method = RequestMethod.GET)
    public ResponseEntity<?> getStartedNotFinishedTasks(@PathVariable Integer projectID) {

        List<Task> taskList = this.taskService.getProjectUnFinishedTasks(projectsService.getProjectById(projectID));

        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }
}



