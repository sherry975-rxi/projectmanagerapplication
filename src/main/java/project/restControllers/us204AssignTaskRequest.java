package project.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

@RestController
@RequestMapping("/{taskID}/taskTeam")
public class us204AssignTaskRequest {

    private UserService userService;
    private TaskService taskService;
    private ProjectService projectService;
    private User user;
    private Project project;

    @Autowired
    public us204AssignTaskRequest(UserService userService, TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public User getUserByEmail (String email){
        return this.user = userService.getUserByEmail(email);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Project getProjectFromTask (@PathVariable String taskID){
        Task task = taskService.getTaskByTaskID(taskID);
        return this.project = task.getProject();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createRequestAddCollabToTask (@PathVariable String taskID, int effort){
        ProjectCollaborator projectCollaborator;
        Task task = taskService.getTaskByTaskID(taskID);
        if(this.projectService.isUserActiveInProject(this.user, this.project)){
            projectCollaborator = this.projectService.findActiveProjectCollaborator(this.user, this.project);
            task.createTaskAssignementRequest(projectCollaborator);
        }
        else{
            projectCollaborator = this.project.createProjectCollaborator(this.user, effort);
            task.createTaskAssignementRequest(projectCollaborator);
        }
    }
}
