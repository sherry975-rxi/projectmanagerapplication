package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import org.springframework.web.util.UriComponents;
import project.dto.UserDTO;
import project.model.*;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;


import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * This rest controller allows a collaborator to create a request of assignment to a specific task
 */
@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}")
public class RestRequestController {

    private UserService userService;
    private TaskService taskService;
    private ProjectService projectService;
    private String requestDetail = "Request details";
    private String allRequests = "List of All Requests";
    private String requestType;
    private String removalList = "List of Removal Requests";
    private String assignList = "List of Assignment Requests";
    private static final String ASSIGNMENT = "assignment";
    private static final String REMOVAL = "removal";


    @Autowired
    public RestRequestController(UserService userService, TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    /**
     * This method allows the collaborator to get a
     * list of pending requests from a specific task.
     *
     * @param taskId Task id associated to the task to be made the request
     * @param projectId Project id associated to the project where the task belongs
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public ResponseEntity<List<TaskTeamRequest>> getAllRequests(@PathVariable String taskId, @PathVariable int projectId) {

        projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        List<TaskTeamRequest> requestsList = task.getPendingTaskTeamRequests();
        for(TaskTeamRequest request : requestsList){

            Link reference = linkTo(methodOn(getClass()).getRequestDetails(request.getDbId(), taskId, projectId)).withRel(requestDetail);
            request.add(reference);
        }

        return new ResponseEntity<>(requestsList, HttpStatus.OK);

    }

    /**
     * This method allows the collaborator to choose between getting a
     * list of assignment or removal pending requests from a specific task.
     *
     * @param reqType Type of request: assignment or removal
     * @param taskId Task id associated to the task to be made the request
     * @param projectId Project id associated to the project where the task belongs
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests/filter/{reqType}", method = RequestMethod.GET)
    public ResponseEntity<List<TaskTeamRequest>> getAllFilteredRequests( @PathVariable String reqType, @PathVariable String taskId, @PathVariable int projectId) {

        projectService.getProjectById(projectId);
        Task task = taskService.getTaskByTaskID(taskId);


        if (ASSIGNMENT.equals(reqType)) {

            List<TaskTeamRequest> assignRequestList = task.getPendingTaskAssignmentRequests();
            for(TaskTeamRequest request : task.getPendingTaskAssignmentRequests()){

                Link reference = linkTo(methodOn(getClass()).getRequestDetails(request.getDbId(), taskId, projectId)).withRel(requestDetail);
                request.add(reference);
            }

            return new ResponseEntity<>(assignRequestList, HttpStatus.OK);


        } else if (REMOVAL.equals(reqType)) {

            List<TaskTeamRequest> removalRequestList = task.getPendingTaskRemovalRequests();
            for(TaskTeamRequest request : removalRequestList){

                Link reference = linkTo(methodOn(getClass()).getRequestDetails(request.getDbId(), taskId, projectId)).withRel(requestDetail);
                request.add(reference);
            }

            return new ResponseEntity<>(removalRequestList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * This method returns a specific request.
     *
     * @param requestId Request id associated to the request
     * @param taskId    Task id associated to the task to be made the request
     * @param projectId Project id associated to the project where the task belongs
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests/{requestId}", method = RequestMethod.GET)
    public ResponseEntity<TaskTeamRequest> getRequestDetails(@PathVariable int requestId, @PathVariable String taskId, @PathVariable int projectId) {

        projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        for (TaskTeamRequest request : task.getPendingTaskTeamRequests()) {
            if (request.getDbId() == requestId) {

                Link selfLink = linkTo(methodOn(RestRequestController.class).getAllRequests(taskId, projectId)).slash(request.getDbId()).withSelfRel();
                request.add(selfLink);

                return new ResponseEntity<>(request, HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

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
    public ResponseEntity<TaskTeamRequest> createAssignmentRequest (@PathVariable String taskId, @PathVariable int projectId, @RequestBody UserDTO userDTO){

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        String userDTOEmail = userDTO.getEmail();

        User user = userService.getUserByEmail(userDTOEmail);

        ProjectCollaborator projCollab = projectService.findActiveProjectCollaborator(user, project);

        if (task.getCurrentState()==StateEnum.CANCELLED) {

            // user, project and task exist but task is cancelled in this project
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

        } else

        if (projCollab != null && !task.isProjectCollaboratorActiveInTaskTeam(projCollab) && !task.isAssignmentRequestAlreadyCreated(projCollab)) {

            task.createTaskAssignmentRequest(projCollab);
            taskService.saveTask(task);

            Optional<TaskTeamRequest> requestFound = task.getPendingTaskTeamRequests().stream()
                    .filter(request -> request.getProjCollab().equals(projCollab))
                    .filter(TaskTeamRequest::isAssignmentRequest)
                    .findFirst();

            TaskTeamRequest assignRequestCreated = requestFound.orElseThrow(null);

            Link reference = linkTo(methodOn(getClass()).getRequestDetails(assignRequestCreated.getDbId(), taskId, projectId)).withRel(requestDetail);

            requestType = ASSIGNMENT;
            Link referenceTwo = linkTo(methodOn(getClass()).getAllFilteredRequests(requestType, taskId, projectId)).withRel(assignList);

            assignRequestCreated.add(reference);
            assignRequestCreated.add(referenceTwo);

            UriComponents ucb =linkTo(methodOn(getClass()).getRequestDetails(assignRequestCreated.getDbId(), taskId, projectId)).toUriComponentsBuilder().build();

            URI location = ucb.toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);

            return new ResponseEntity<>(assignRequestCreated, headers, HttpStatus.CREATED);

        }

        // user is not in this project, or is active in task, or request already exists
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    /**
     * This method allows the collaborator to create a request of removal from a specific task.
     *
     * @param taskId    Task id associated to the task to be made the request
     * @param projectId Project id associated to the project where the task belongs
     * @param userDTO   User related to the collaborator that wants to make the request.
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests/removalRequest", method = RequestMethod.POST)
    public ResponseEntity<TaskTeamRequest> createRemovalRequest (@PathVariable String taskId,
                                                                 @PathVariable int projectId, @RequestBody UserDTO userDTO){

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        String userDTOEmail = userDTO.getEmail();

        User user = userService.getUserByEmail(userDTOEmail);

        ProjectCollaborator projCollab = projectService.findActiveProjectCollaborator(user, project);

        if (task.getCurrentState()==StateEnum.CANCELLED) {

            // user, project and task exist but task is cancelled in this project
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

        } else

        if (projCollab != null && task.isProjectCollaboratorActiveInTaskTeam(projCollab) && !task.isRemovalRequestAlreadyCreated(projCollab)) {

            task.createTaskRemovalRequest(projCollab);
            taskService.saveTask(task);

            Optional<TaskTeamRequest> requestFound = task.getPendingTaskTeamRequests().stream()
                    .filter(taskTeamRequest -> taskTeamRequest.getProjCollab().equals(projCollab))
                    .filter(TaskTeamRequest::isRemovalRequest)
                    .findFirst();

            TaskTeamRequest removalRequestCreated = requestFound.orElseThrow(null);

            Link reference = linkTo(methodOn(getClass()).getRequestDetails(removalRequestCreated.getDbId(), taskId, projectId)).withRel(requestDetail);

            requestType = REMOVAL;
            Link referenceTwo = linkTo(methodOn(getClass()).getAllFilteredRequests(requestType, taskId, projectId)).withRel(removalList);

            removalRequestCreated.add(reference);
            removalRequestCreated.add(referenceTwo);

            UriComponents ucb =linkTo(methodOn(getClass()).getRequestDetails(removalRequestCreated.getDbId(), taskId, projectId)).toUriComponentsBuilder().build();

            URI location = ucb.toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);

            return new ResponseEntity<>(removalRequestCreated, headers, HttpStatus.CREATED);


        }

        // user is not in this project, or is not active in task, or request already exists
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }


    /**
     * This method returns a specific request.
     *
     * @param requestId Request id associated to the request
     * @param taskId    Task id associated to the task to be made the request
     * @param projectId Project id associated to the project where the task belongs
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests/{requestId}/approval", method = RequestMethod.PUT)
    public ResponseEntity<TaskTeamRequest> approveRequest(@PathVariable int requestId, @PathVariable String taskId, @PathVariable int projectId) {

        projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        Optional<TaskTeamRequest> requestFound = task.getPendingTaskTeamRequests().stream()
                .filter(request -> request.getDbId()==requestId)
                .findFirst();

        if(requestFound.isPresent()) {

            TaskTeamRequest requestToApprove = requestFound.get();

            if (requestToApprove.isAssignmentRequest()) {

                ProjectCollaborator projectCollaboratorToAdd = requestToApprove.getProjCollab();
                task.addProjectCollaboratorToTask(projectCollaboratorToAdd);
                requestToApprove.setApprovalDate(Calendar.getInstance());
                task.deleteTaskAssignmentRequest(projectCollaboratorToAdd);

                taskService.saveTask(task);

                Link reference = linkTo(methodOn(getClass()).getAllRequests(taskId, projectId)).withRel(allRequests);

                requestType = ASSIGNMENT;
                Link referenceTwo = linkTo(methodOn(getClass()).getAllFilteredRequests(requestType, taskId, projectId)).withRel(assignList);

                requestToApprove.add(reference);
                requestToApprove.add(referenceTwo);

                return new ResponseEntity<>(requestToApprove, HttpStatus.OK);
            } else if (requestToApprove.isRemovalRequest()) {

                ProjectCollaborator projectCollaboratorToRemove = requestToApprove.getProjCollab();
                task.removeProjectCollaboratorFromTask(projectCollaboratorToRemove);
                requestToApprove.setApprovalDate(Calendar.getInstance());
                task.deleteTaskRemovalRequest(projectCollaboratorToRemove);
                taskService.saveTask(task);

                Link reference = linkTo(methodOn(getClass()).getAllRequests(taskId, projectId)).withRel(allRequests);

                requestType = REMOVAL;
                Link referenceTwo = linkTo(methodOn(getClass()).getAllFilteredRequests(requestType, taskId, projectId)).withRel(removalList);

                requestToApprove.add(reference);
                requestToApprove.add(referenceTwo);


                return new ResponseEntity<>(requestToApprove, HttpStatus.OK);

            }
        }




        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /**
     * This method returns a specific request.
     *
     * @param requestId Request id associated to the request
     * @param taskId    Task id associated to the task to be made the request
     * @param projectId Project id associated to the project where the task belongs
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests/{requestId}/reject", method = RequestMethod.PUT)
    public ResponseEntity<TaskTeamRequest> rejectRequest(@PathVariable int requestId, @PathVariable String taskId, @PathVariable int projectId) {

        projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        Optional<TaskTeamRequest> requestFound = task.getPendingTaskTeamRequests().stream()
                .filter(request -> request.getDbId()==requestId)
                .findFirst();

        if(requestFound.isPresent()) {

            TaskTeamRequest requestToReject = requestFound.get();

            if (requestToReject.isAssignmentRequest()) {

                ProjectCollaborator projectCollaboratorToAdd = requestToReject.getProjCollab();
                requestToReject.setRejectDate(Calendar.getInstance());
                task.deleteTaskAssignmentRequest(projectCollaboratorToAdd);

                taskService.saveTask(task);

                Link reference = linkTo(methodOn(getClass()).getAllRequests(taskId, projectId)).withRel(allRequests);

                requestType = ASSIGNMENT;
                Link referenceTwo = linkTo(methodOn(getClass()).getAllFilteredRequests(requestType, taskId, projectId)).withRel(assignList);

                requestToReject.add(reference);
                requestToReject.add(referenceTwo);

                return new ResponseEntity<>(requestToReject, HttpStatus.OK);
            } else if (requestToReject.isRemovalRequest()) {

                ProjectCollaborator projectCollaboratorToRemove = requestToReject.getProjCollab();
                requestToReject.setRejectDate(Calendar.getInstance());
                task.deleteTaskRemovalRequest(projectCollaboratorToRemove);
                taskService.saveTask(task);

                Link reference = linkTo(methodOn(getClass()).getAllRequests(taskId, projectId)).withRel(allRequests);

                requestType = REMOVAL;
                Link referenceTwo = linkTo(methodOn(getClass()).getAllFilteredRequests(requestType, taskId, projectId)).withRel(removalList);

                requestToReject.add(reference);
                requestToReject.add(referenceTwo);


                return new ResponseEntity<>(requestToReject, HttpStatus.OK);

            }
        }




        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}

