package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}")
public class US204AssignTaskRequestRestController {

    private UserService userService;
    private TaskService taskService;
    private ProjectService projectService;


    @Autowired
    public US204AssignTaskRequestRestController(UserService userService, TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }



    @RequestMapping(value = "/CreateAssignmentRequest" , method = RequestMethod.POST)
    public ResponseEntity<?> createRequestAddCollabToTask (@PathVariable String taskId, @PathVariable int projectId, @RequestHeader String userEmail){
        ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ProjectCollaborator projectCollaborator;

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByEmail(userEmail);

            if(task.createTaskAssignementRequest(this.projectService.findActiveProjectCollaborator(user, project))&&!task.isProjectCollaboratorInTaskTeam(this.projectService.findActiveProjectCollaborator(user, project))){
                this.taskService.saveTask(task);
                result = new ResponseEntity<>(HttpStatus.OK);
            }


        return result;
    }
}
