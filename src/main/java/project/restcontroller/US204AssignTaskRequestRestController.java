package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import project.model.*;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * This rest controller allows a collaborator to create a request of assignment to a specific task
 */
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
    /**
     * This method allows the collaborator to get a
     * list of pending requests from a specific task.
     *
     * @param taskId
     *          Task id associated to the task to be made the request
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests" , method = RequestMethod.GET)
    public ResponseEntity<List<TaskTeamRequest>> getAllRequests (@PathVariable String taskId) {


        Task task = taskService.getTaskByTaskID(taskId);

        return new ResponseEntity<>(task.getPendingTaskTeamRequests(), HttpStatus.OK);

    }

    /**
     * This method allows the collaborator to choose between getting a
     * list of assignment or removal pending requests from a specific task.
     *
     * @param taskId
     *          Task id associated to the task to be made the request
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests/{reqType}" , method = RequestMethod.GET)
    public ResponseEntity<List<TaskTeamRequest>> getAllFilteredRequests (@PathVariable String taskId, @PathVariable String reqType) {


        Task task = taskService.getTaskByTaskID(taskId);


        if ("assignment".equals(reqType)){

            return new ResponseEntity<>(task.getPendingTaskAssignmentRequests(), HttpStatus.OK);
        }
        else if ("removal".equals(reqType)){

            return ResponseEntity.ok(task.getPendingTaskRemovalRequests());
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.FORBIDDEN);
    }

    /**
     * This method allows the collaborator to create a request of assignment to a specific task.
     *
     * @param taskId    Task id associated to the task to be made the request
     * @param projectId Project id associated to the project where the task belongs
     * @param userDTO   User related to the collaborator that wants to make the request.
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests/assignmentRequest", method = RequestMethod.POST)
    public ResponseEntity<TaskTeamRequest> createAssignmentRequest(@PathVariable String taskId, @PathVariable int projectId, @RequestBody User userDTO) {

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        String userDTOEmail = userDTO.getEmail();

        User user = userService.getUserByEmail(userDTOEmail);

        Optional<User> userOptional = Optional.of(user);

        if (userOptional.isPresent()) {
            //userOptional.get();


            ProjectCollaborator projCollab = projectService.findActiveProjectCollaborator(user, project);

            if (projCollab != null && !task.isProjectCollaboratorActiveInTaskTeam(projCollab) && !task.isAssignmentRequestAlreadyCreated(projCollab)) {

                task.createTaskAssignmentRequest(projCollab);
                taskService.saveTask(task);

                for (TaskTeamRequest other : task.getPendingTaskTeamRequests()) {
                    if (other.getTask().equals(task) && other.getProjCollab().equals(projCollab) && other.isAssignmentRequest()) {
                        TaskTeamRequest requestCreated = other;

                        return new ResponseEntity<>(requestCreated, HttpStatus.CREATED);
                    }
                }

            }
            // user, project and task exist but user is not in this project
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        // either user, project or task don't exist
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}