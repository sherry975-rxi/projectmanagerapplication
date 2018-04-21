package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

@RestController
@RequestMapping("projects/{projid}/tasks/{taskid}/requests/")
public class RestRequestController {


    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public RestRequestController(ProjectService projectService, TaskService taskService, UserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

}
