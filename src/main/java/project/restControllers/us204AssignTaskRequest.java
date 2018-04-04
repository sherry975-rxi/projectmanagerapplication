package project.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskID}")
public class us204AssignTaskRequest {

    private UserService userService;
    private TaskService taskService;
    private ProjectService projectService;
    private User user;

    @Autowired
    public us204AssignTaskRequest(UserService userService, TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }


    @RequestMapping(value = "/CreateAssignmentRequest" , method = RequestMethod.POST)
    public void createRequestAddCollabToTask (@PathVariable String taskID, @PathVariable int projectId, @RequestBody String email){
        ProjectCollaborator projectCollaborator;

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskID);

        this.user = userService.getUserByEmail(email);

        if(this.projectService.isUserActiveInProject(this.user, project)){
            projectCollaborator = this.projectService.findActiveProjectCollaborator(this.user, project);
            task.createTaskAssignementRequest(projectCollaborator);
        }
    }
}
