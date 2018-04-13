package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.Task;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;



/**
 * This rest controller allows a collaborator to create a request of assignment to a specific task
 */
@RestController
@RequestMapping("/users/{userId}/projects/{projectId}/tasks/{taskId}")
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

    @RequestMapping(value = "/requests" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllRequests (@PathVariable String taskId, @PathVariable int projectId, @PathVariable  int userId) {
        ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByID(userId);


        if (task.getPendingTaskTeamRequests().size()>0) {
            result = new ResponseEntity<>(task.getPendingTaskTeamRequests(), HttpStatus.OK);
        }

        return result;
    }

    @RequestMapping(value = "/requests/{reqType}" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllFilteredRequests (@PathVariable String taskId, @PathVariable String reqType , @PathVariable int projectId, @PathVariable  int userId) {
        ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByID(userId);

        if ("assignment".equals(reqType)){

            result = new ResponseEntity<>(task.getPendingTaskAssignmentRequests(), HttpStatus.OK);
        }
        else if ("removal".equals(reqType)){

            result = new ResponseEntity<>(task.getPendingTaskRemovalRequests(), HttpStatus.OK);
        }

        return result;
    }

    /**
     * This method allows the collaborator to create a request of assignment to a specific task.
     *
     * @param taskId
     *          Task id associated to the task to be made the request
     * @param projectId
     *          Project id associated to the project where the task belongs
     * @param userId
     *          User id related to the collaborator that wants to make the request.
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests/assignmentRequest" , method = RequestMethod.POST)
    public ResponseEntity<?> createRequestAddCollabToTask (@PathVariable String taskId, @PathVariable int projectId, @PathVariable  int userId){
        ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByID(userId);

            if(task.createTaskAssignmentRequest(this.projectService.findActiveProjectCollaborator(user, projectService.getProjectById(projectId)))&&!task.isProjectCollaboratorInTaskTeam(this.projectService.findActiveProjectCollaborator(user, projectService.getProjectById(projectId)))){
                this.taskService.saveTask(task);
                result = new ResponseEntity<>(HttpStatus.OK);
            }
        return result;
    }
}
