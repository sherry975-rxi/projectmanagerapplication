package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import project.model.*;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


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
     * @param taskId Task id associated to the task to be made the request
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public ResponseEntity<List<TaskTeamRequest>> getAllRequests(@PathVariable String taskId, @PathVariable int projectId) {

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        List<TaskTeamRequest> requestsList = task.getPendingTaskTeamRequests();
        for(TaskTeamRequest request : requestsList){

            Link order = linkTo(methodOn(getClass()).getRequestDetails(request.getDbId(), taskId, projectId)).withRel("Request details");
            request.add(order);
        }

        return new ResponseEntity<>(requestsList, HttpStatus.OK);

    }

    /**
     * This method allows the collaborator to choose between getting a
     * list of assignment or removal pending requests from a specific task.
     *
     * @param taskId Task id associated to the task to be made the request
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests/list/{reqType}", method = RequestMethod.GET)
    public ResponseEntity<List<TaskTeamRequest>> getAllFilteredRequests(@PathVariable String taskId, @PathVariable String reqType, @PathVariable int projectId) {

        Project project = projectService.getProjectById(projectId);
        Task task = taskService.getTaskByTaskID(taskId);


        if ("assignment".equals(reqType)) {

            List<TaskTeamRequest> assignRequestList = task.getPendingTaskAssignmentRequests();
            for(TaskTeamRequest request : assignRequestList){

                Link order = linkTo(methodOn(getClass()).getRequestDetails(request.getDbId(), taskId, projectId)).withRel("Request details");
                request.add(order);
            }

            return new ResponseEntity<>(assignRequestList, HttpStatus.OK);


        } else if ("removal".equals(reqType)) {

            List<TaskTeamRequest> removalRequestList = task.getPendingTaskAssignmentRequests();
            for(TaskTeamRequest request : removalRequestList){

                Link order = linkTo(methodOn(getClass()).getRequestDetails(request.getDbId(), taskId, projectId)).withRel("Request details");
                request.add(order);
            }

            return new ResponseEntity<>(removalRequestList, HttpStatus.OK);
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.FORBIDDEN);
    }

    /**
     * This method returns a specific request.
     *
     * @param requestId Request id associated to the request
     * @param taskId    Task id associated to the task to be made the request
     * @return ResponseEntity
     */
    @RequestMapping(value = "/requests/{requestId}", method = RequestMethod.GET)
    public ResponseEntity<TaskTeamRequest> getRequestDetails(@PathVariable int requestId, @PathVariable String taskId, @PathVariable int projectId) {

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        for (TaskTeamRequest other : task.getPendingTaskTeamRequests()) {
            if (other.getDbId() == requestId) {

                Link selfLink = linkTo(methodOn(US204AssignTaskRequestRestController.class).getAllRequests(taskId, projectId)).slash(other.getDbId()).withSelfRel();
                other.add(selfLink);

                return new ResponseEntity<>(other, HttpStatus.OK);
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
        public ResponseEntity<TaskTeamRequest> createAssignmentRequest (@PathVariable String taskId,
        @PathVariable int projectId, @RequestBody User userDTO){

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

                    for (TaskTeamRequest request : task.getPendingTaskTeamRequests()) {
                        if (request.getTask().equals(task) && request.getProjCollab().equals(projCollab) && request.isAssignmentRequest()) {

                            Link order = linkTo(methodOn(getClass()).getRequestDetails(request.getDbId(), taskId, projectId)).withRel("Request details");
                            request.add(order);
                            //Link ordersLink = linkTo(getRequestDetails(request.getDbId(), taskId)).withRel("Request Details");
//                            Link methodLinkBuilder =
//                                    methodOn(US204AssignTaskRequestRestController.class).getAllRequests(taskId);
//                            Link ordersLink = linkTo(methodLinkBuilder).withRel("allOrders");

                            //Link reference = linkTo(getRequestDetails(request.getDbId(), taskId)).withRel("Request Details");
//                            Link reference = linkTo(US204AssignTaskRequestRestController.class).slash(request.getDbId()).withRel("Request Details");
//
////
//                            request.add(reference);
                            //Link referenceTwo = linkTo(methodOn( getRequestDetails(request.getDbId(), taskId)).toUri();
                            //URI result = MvcUriComponentsBuilder.fromController(getClass()).path("/requests/{requestId}").buildAndExpand(request.getDbId()).toUri();
                            //URI result = MvcUriComponentsBuilder.fromMethodCall(getRequestDetails(request.getDbId(), taskId, projectId)).build().toUri();
//                                    fromMethod(getClass(), getRequestDetails(request.getDbId(),taskId)).buildAndExpand()
//                                    fromController(getClass()).path("/requests/{requestId}").buildAndExpand(request.getDbId()).toUri();

                            UriComponents ucbT =linkTo(methodOn(getClass()).getRequestDetails(request.getDbId(), taskId, projectId)).toUriComponentsBuilder().build();

                            //UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(order.toString()).build();
                            URI teste = ucbT.toUri();


                            /*URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/requests/{id}").buildAndExpand(request.getDbId()).toUri();
                            URI zzzz = ServletUriComponentsBuilder.fromRequest(methodOn(getClass()).getRequestDetails(request.getDbId(), taskId, projectId);*/


                            //URI locationZ = MvcUriComponentsBuilder.fromMethodName(getClass(), "getRequestDetails", request.getDbId(), taskId, projectId).path("/{id}").buildAndExpand(request.getDbId()).toUri();

                            //URI locationTwo = linkTo(getRequestDetails(request.getDbId(), taskId)).toUri();
                            HttpHeaders headers = new HttpHeaders();
                            headers.setLocation(teste);


                            //URI location = ServletUriComponentsBuilder.fromRequestUri(getAllRequests(taskId))

                            return new ResponseEntity<>(request, headers, HttpStatus.CREATED);
                            //return ResponseEntity.created(locationZ).body(request);
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

