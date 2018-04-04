package project.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.services.ProjectService;
import project.services.TaskService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projects/{projectID}/tasks")
public class US372GetStartedNoFinishedProjectTasksRestController {

    private final ProjectService projectsService;

    private final TaskService taskService;


    @Autowired
    public US372GetStartedNoFinishedProjectTasksRestController(ProjectService projServ, TaskService taskServ) {


        this.projectsService = projServ;
        this.taskService = taskServ;
    }

    @RequestMapping(value = "unfinished", method = RequestMethod.GET)
    List<String> getStartedNotFinishedTasks(@PathVariable Integer projectID) {
        this.validateProject(projectID);
        // return this.taskService.getProjectUnFinishedTasks(projectsService.getProjectById(projectID));
        List<String> teste = new ArrayList<>();
        teste.add("ola");
        return teste;
    }


    private void validateProject(int projectId) {
        this.projectsService.getProjectById(projectId);
        String a;

    }
}



